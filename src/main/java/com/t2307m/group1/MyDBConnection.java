package com.t2307m.group1;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MyDBConnection {
    public static MongoCollection<Document> getCollection() {
        String connectionString = "mongodb://localhost:27017"; // URL truy cập vào server MongoDB
        String dbName = "eShop"; // Tên Session1.database
        String collectionName ="OrderCollection";
        // Tạo client MongoDB
        MongoClient mongoClient = MongoClients.create(connectionString);

        // Lấy Session1.database từ client
        MongoDatabase database = mongoClient.getDatabase(dbName);

        MongoCollection<Document> collection = database.getCollection(collectionName);

        return collection;
    }

    public static void main(String[] args) {
        // Gọi phương thức để kiểm tra kết nối
        MongoCollection<Document> collection = getCollection();
        if (collection != null) {
            System.out.println("Connect to MongoDB success!");
        }
    }
}
