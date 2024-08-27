package com.t2307m.group1;

import org.bson.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;


public class View {
    Controller controller = new Controller();
    private final BufferedReader reader;
MongoDataGenerator mongoDataGenerator = new MongoDataGenerator();
    public View () {

        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }



    private int menu() throws IOException {
        System.out.println("=======MENU ORDER==============");
        System.out.println("1. Add new 10000 document");
        System.out.println("2. Edit delivery_address by orderid.");
        System.out.println("3. Remove an order.");
        System.out.println("4. Read all product by orderid.");
        System.out.println("5. Calculate total amount.");
        System.out.println("6. Count total product_id equal “somi”");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
        return Integer.parseInt(reader.readLine());
    }

    private void updateDeliveryAddressByOrderId() throws IOException {
        System.out.print("Enter orderId: ");
        int orderId = Integer.parseInt(reader.readLine());
        System.out.print("Enter new deliveryAddress: ");
        String newDeliveryAddress = reader.readLine();
        controller.updateDeliveryAddressByOrderId(orderId, newDeliveryAddress);
    }

    private void removeOrderById() throws IOException {
        System.out.print("Enter orderId: ");
        int orderId = Integer.parseInt(reader.readLine());
        controller.deleteOrder(orderId);
    }

    private void readAllProductsById() throws IOException {
        System.out.print("Enter orderId: ");
        int orderId = Integer.parseInt(reader.readLine());
        List<Document> products = controller.getProductsByOrderId(orderId);
        if (products.isEmpty()) {
            System.out.println("No products found for the given orderId.");
            return;
        }
        System.out.println("| No | Product name    | Price   | Quantity | Total |");
        int index = 1;
        for (Document product : products) {
            String productName = product.getString("product_name");
            double price = product.getDouble("price");
            int quantity = product.getInteger("quantity");
            double total = price * quantity;
            System.out.printf("| %-2d | %-15s | %-7.2f | %-8d | %-5.2f |\n",
                    index++, productName, price, quantity, total);
        }
    }

    private void calculateTotalAmount() throws IOException {
        double total = controller.calculateTotalAmount();
        System.out.println("Total amount: " + total);
    }

    private void countProductIdEqualSomi() throws IOException {
        int count = controller.countTotalProductById("somi");
        System.out.println("Total product id: " + count);
    }
    public void start() throws IOException {
        boolean running = true;
        while (running) {
            int choice = menu();
            switch (choice) {
                case 1:
                    mongoDataGenerator.addNewDocument();
                    break;
                case 2:
                    updateDeliveryAddressByOrderId();
                    break;
                case 3:
                    removeOrderById();
                    break;
                case 4:
                    readAllProductsById();
                    break;
                case 5:
                    calculateTotalAmount();
                    break;
                case 6:
                    countProductIdEqualSomi();
                    break;
                case 7:
                    running = false;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please select again.");
            }
        }
    }
}
