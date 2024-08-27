package com.t2307m.group1;

import com.github.javafaker.Faker;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.*;

public class MongoDataGenerator {
    public void addNewDocument() {
        System.out.println("Bắt đầu thêm bản ghi, vui lòng chờ!");
        MongoCollection<Document> salesCollection = MyDBConnection.getCollection();
        Faker faker = new Faker();
        Random random = new Random();

        // Cấu trúc danh sách các sản phẩm
        List<Document> productList = List.of(
                new Document("product_id", "quanau").append("product_name", "quan au").append("size", "XL").append("price", 10.0),
                new Document("product_id", "somi").append("product_name", "ao so mi").append("size", "XL").append("price", 10.5)
        );

        Set<Integer> uniqueOrderIds = new HashSet<>();

//        LocalDate startDate = LocalDate.of(2021, 1, 1);
//        LocalDate endDate = LocalDate.of(2024, 12, 31);
//        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

        // Tạo 10000 bản ghi
        List<Document> saleRecords = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
//            String customerId = UUID.randomUUID().toString();
            int orderId;
            do {
                orderId = random.nextInt(1000000) + 1; // Tạo số nguyên ngẫu nhiên từ 1 đến 1,000,000
            } while (!uniqueOrderIds.add(orderId)); // Thêm vào set và kiểm tra trùng lặp

            // Tạo thông tin về các sản phẩm trong đơn hàng
            List<Document> products = new ArrayList<>();
            int numProducts = random.nextInt(2) + 1; // Chọn 1 hoặc 2 sản phẩm cho mỗi đơn hàng
            for (int j = 0; j < numProducts; j++) {
                Document product = productList.get(random.nextInt(productList.size()));
                int quantity = random.nextInt(3) + 1; // Số lượng mỗi sản phẩm từ 1 đến 3
                products.add(new Document("product_id", product.getString("product_id"))
                        .append("product_name", product.getString("product_name"))
                        .append("size", product.getString("size"))
                        .append("price", product.getDouble("price"))
                        .append("quantity", quantity));
            }

            // Tính tổng tiền đơn hàng
            double totalAmount = products.stream()
                    .mapToDouble(product -> product.getDouble("price") * product.getInteger("quantity"))
                    .sum();

            // Chọn ngày ngẫu nhiên và chuyển đổi sang dạng `Date`
//            LocalDate randomDate = startDate.plusDays(random.nextInt((int) daysBetween + 1));
//            LocalDateTime randomDateTime = randomDate.atTime(random.nextInt(24), random.nextInt(60), random.nextInt(60));
//            Date saleDate = Date.from(randomDateTime.atZone(ZoneId.systemDefault()).toInstant());
//
//            // Định dạng ngày theo định dạng của `Faker`
//            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
//            String formattedDate = dateFormat.format(saleDate);

            // Tạo bản ghi mẫu
            Document saleRecord = new Document("orderid", orderId)
                    .append("products", products)
                    .append("total_amount", totalAmount)
                    .append("delivery_address", faker.address().fullAddress());

            saleRecords.add(saleRecord);
        }

        // Chèn dữ liệu vào MongoDB
        salesCollection.insertMany(saleRecords);
        System.out.println("Đã chèn thành công 10000 bản ghi vào collection sales");
    }


}
