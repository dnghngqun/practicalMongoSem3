package com.t2307m.group1;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.management.Query;
import java.util.List;

public class Service {
    MongoCollection<Document> collection = MyDBConnection.getCollection();
    public Service() {}

    public void updateDeliveryAddressByOrderId(int orderId, String newAddress){
        try {
            // Thực hiện cập nhật trong MongoDB
            collection.updateOne(
                    Filters.eq("orderid", orderId),
                    Updates.set("delivery_address", newAddress)
            );
            System.out.println("Cập nhật thành công!");

        } catch (MongoException e) {
            // Xử lý lỗi MongoDB
            System.err.println("Lỗi khi cập nhật địa chỉ giao hàng: " + e.getMessage());
        } catch (Exception e) {
            // Xử lý các lỗi khác
            System.err.println("Có lỗi xảy ra: " + e.getMessage());
        }
    }

    public void deleteOrder(int orderId){
        try {
            // Xóa đơn hàng trong MongoDB
            collection.deleteOne(Filters.eq("orderid", orderId));
            System.out.println("Đã xóa đơn hàng thành công!");

        } catch (MongoException e) {
            // Xử lý lỗi MongoDB
            System.err.println("Lỗi khi xóa đơn hàng: " + e.getMessage());
        } catch (Exception e) {
            // Xử lý các lỗi khác
            System.err.println("Có lỗi xảy ra: " + e.getMessage());
        }
    }
    public List<Document> getProductsByOrderId(int orderId) {
        List<Document> products = null;

        try {
            // Tạo điều kiện truy vấn dựa trên orderid
            Bson filter = Filters.eq("orderid", orderId);

            // Truy vấn document theo orderid
            Document order = collection.find(filter).first();

            if (order != null) {
                // Lấy danh sách sản phẩm từ document
                products = (List<Document>) order.get("products");
            } else {
                System.out.println("Không tìm thấy đơn hàng với orderid: " + orderId);
            }

        } catch (MongoException e) {
            // Xử lý lỗi MongoDB
            System.err.println("Lỗi khi lấy thông tin sản phẩm: " + e.getMessage());
        } catch (Exception e) {
            // Xử lý các lỗi khác
            System.err.println("Có lỗi xảy ra: " + e.getMessage());
        }

        return products;
    }

    public double calculateTotalAmount(){
        double totalAmount = 0;

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document order = cursor.next();
                // Lấy giá trị của trường total_amount và cộng dồn vào totalAmount
                double amount = order.getDouble("total_amount");
                totalAmount += amount;
            }

        } catch (MongoException e) {
            System.err.println("Lỗi khi tính tổng số tiền: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Có lỗi xảy ra: " + e.getMessage());
        }
        return totalAmount;
    }

    public int countTotalProductById(String productId) {
        int totalCount = 0;

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document order = cursor.next();
                List<Document> products = (List<Document>) order.get("products");

                for (Document product : products) {
                    if (productId.equals(product.getString("product_id"))) {
                        totalCount += product.getInteger("quantity");
                    }
                }
            }

        } catch (MongoException e) {
            System.err.println("Lỗi khi đếm sản phẩm: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Có lỗi xảy ra: " + e.getMessage());
        }

        return totalCount;
    }
}
