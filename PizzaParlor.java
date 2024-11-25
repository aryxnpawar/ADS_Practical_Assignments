

import java.util.Scanner;

public class PizzaParlor {
    private final String[] orders; // Array to store orders
    private final int maxSize;     // Maximum number of orders
    private int front, rear;       // Front and rear pointers for the circular queue

    // Constructor
    public PizzaParlor(int maxSize) {
        this.maxSize = maxSize;
        this.orders = new String[maxSize];
        this.front = this.rear = -1;
    }

    // Check if the queue is full
    public boolean isFull() {
        return (rear + 1) % maxSize == front;
    }

    // Check if the queue is empty
    public boolean isEmpty() {
        return front == -1 && rear == -1;
    }

    // Place an order
    public boolean placeOrder(String order) {
        if (isFull()) {
            System.out.println("Order Queue is Full! Cannot place new order.");
            return false;
        }

        if (isEmpty()) {
            // If the queue is empty, initialize front and rear
            front = rear = 0;
        } else {
            // Move rear to the next position
            rear = (rear + 1) % maxSize;
        }

        orders[rear] = order;
        System.out.println("Order Placed: " + order);
        return true;
    }

    // Serve an order
    public String serveOrder() {
        if (isEmpty()) {
            System.out.println("No Orders to Serve!");
            return null;
        }

        String servedOrder = orders[front];

        if (front == rear) {
            // If only one order is present, reset the queue
            front = rear = -1;
        } else {
            // Move front to the next position
            front = (front + 1) % maxSize;
        }

        System.out.println("Order Served: " + servedOrder);
        return servedOrder;
    }

    // Display all orders in the queue
    public void displayOrders() {
        if (isEmpty()) {
            System.out.println("No Orders in the Queue!");
            return;
        }

        System.out.println("Current Orders:");
        int temp = front;
        do {
            System.out.println(orders[temp]);
            temp = (temp + 1) % maxSize;
        } while (temp != (rear + 1) % maxSize);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the maximum number of orders (N): ");
        int maxOrders = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        PizzaParlor pizzaParlor = new PizzaParlor(maxOrders);

        while (true) {
            System.out.println("\n--- Pizza Parlor System ---");
            System.out.println("1. Place Order");
            System.out.println("2. Serve Order");
            System.out.println("3. Display Orders");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter the Order Name: ");
                    String order = scanner.nextLine();
                    pizzaParlor.placeOrder(order);
                    break;

                case 2:
                    pizzaParlor.serveOrder();
                    break;

                case 3:
                    pizzaParlor.displayOrders();
                    break;

                case 4:
                    System.out.println("Exiting the System...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid Choice! Try Again.");
            }
        }
    }
}
