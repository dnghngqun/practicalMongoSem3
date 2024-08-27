package com.t2307m.group1;

import org.bson.Document;

import java.util.List;

public class Controller {
    Service service = new Service();

    public void updateDeliveryAddressByOrderId(int orderId, String newAddress){
       service.updateDeliveryAddressByOrderId(orderId, newAddress);
    }

    public void deleteOrder(int orderId){
        service.deleteOrder(orderId);
    }

    public List<Document> getProductsByOrderId(int orderId){
        return service.getProductsByOrderId(orderId);
    }

    public double calculateTotalAmount(){
        return service.calculateTotalAmount();
    }

    public int countTotalProductById(String productId){
        return service.countTotalProductById(productId);
    }
}
