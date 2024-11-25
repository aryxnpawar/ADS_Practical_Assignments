

import java.util.Scanner;

public class PhoneBook {
    private Node root;

    class Node {
        Node left, right;
        int height, balanceFactor;
        String contactName;
        String phoneNumber;

        public Node(String contactName, String phoneNumber) {
            this.contactName = contactName;
            this.phoneNumber = phoneNumber;
            this.left = this.right = null;
            this.height = this.balanceFactor = 0;
        }
    }

    public PhoneBook() {
        root = null;
    }

    public void addContact(String contactName, String phoneNumber) {
        root = insert(root, contactName, phoneNumber);
    }

    private Node insert(Node currentNode, String contactName, String phoneNumber) {
        if (currentNode == null) return new Node(contactName, phoneNumber);

        if (phoneNumber.compareTo(currentNode.phoneNumber) < 0) {
            currentNode.left = insert(currentNode.left, contactName, phoneNumber);
        } else if (phoneNumber.compareTo(currentNode.phoneNumber) > 0) {
            currentNode.right = insert(currentNode.right, contactName, phoneNumber);
        } else {
            // Duplicate phone numbers are not allowed
            System.out.println("Duplicate phone number not allowed!");
            return currentNode;
        }

        update(currentNode);
        return balance(currentNode);
    }

    public void deleteContact(String phoneNumber) {
        root = delete(root, phoneNumber);
    }

    private Node delete(Node currentNode, String phoneNumber) {
        if (currentNode == null) return null;

        if (phoneNumber.compareTo(currentNode.phoneNumber) < 0) {
            currentNode.left = delete(currentNode.left, phoneNumber);
        } else if (phoneNumber.compareTo(currentNode.phoneNumber) > 0) {
            currentNode.right = delete(currentNode.right, phoneNumber);
        } else {
            // Node found
            if (currentNode.left == null && currentNode.right == null) {
                return null;
            } else if (currentNode.left != null && currentNode.right == null) {
                return currentNode.left;
            } else if (currentNode.left == null && currentNode.right != null) {
                return currentNode.right;
            } else {
                Node successor = leftMost(currentNode.right);
                currentNode.contactName = successor.contactName;
                currentNode.phoneNumber = successor.phoneNumber;
                currentNode.right = delete(currentNode.right, successor.phoneNumber);
            }
        }

        update(currentNode);
        return balance(currentNode);
    }

    private Node leftMost(Node currentNode) {
        while (currentNode.left != null) {
            currentNode = currentNode.left;
        }
        return currentNode;
    }

    public Node searchContact(String phoneNumber) {
        return search(root, phoneNumber);
    }

    private Node search(Node currentNode, String phoneNumber) {
        if (currentNode == null || currentNode.phoneNumber.equals(phoneNumber)) {
            return currentNode;
        }
        return phoneNumber.compareTo(currentNode.phoneNumber) < 0
                ? search(currentNode.left, phoneNumber)
                : search(currentNode.right, phoneNumber);
    }

    public void updateContact(String oldPhoneNumber, String newContactName, String newPhoneNumber) {
        deleteContact(oldPhoneNumber);
        addContact(newContactName, newPhoneNumber);
    }

    public void displayContacts() {
        inOrderTraversal(root);
    }

    private void inOrderTraversal(Node root) {
        if (root == null) return;

        inOrderTraversal(root.left);
        System.out.println("Name: " + root.contactName + ", Phone: " + root.phoneNumber);
        inOrderTraversal(root.right);
    }

    private void update(Node currentNode) {
        int l = -1, r = -1;

        if (currentNode.left != null)
            l = currentNode.left.height;
        if (currentNode.right != null)
            r = currentNode.right.height;

        currentNode.balanceFactor = l - r;
        currentNode.height = 1 + Math.max(l, r);
    }

    private Node balance(Node currentNode) {
        if (currentNode.balanceFactor > 1) {
            if (currentNode.left.balanceFactor >= 0) {
                return rotateRight(currentNode);
            } else {
                currentNode.left = rotateLeft(currentNode.left);
                return rotateRight(currentNode);
            }
        } else if (currentNode.balanceFactor < -1) {
            if (currentNode.right.balanceFactor <= 0) {
                return rotateLeft(currentNode);
            } else {
                currentNode.right = rotateRight(currentNode.right);
                return rotateLeft(currentNode);
            }
        }
        return currentNode;
    }

    private Node rotateRight(Node currNode) {
        Node newRoot = currNode.left;
        currNode.left = newRoot.right;
        newRoot.right = currNode;

        update(currNode);
        update(newRoot);
        return newRoot;
    }

    private Node rotateLeft(Node currNode) {
        Node newRoot = currNode.right;
        currNode.right = newRoot.left;
        newRoot.left = currNode;

        update(currNode);
        update(newRoot);
        return newRoot;
    }

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.addContact("Alice", "1234567890");
        phoneBook.addContact("Bob", "9876543210");
        phoneBook.addContact("Charlie", "5555555555");

        System.out.println("Contacts in PhoneBook:");
        phoneBook.displayContacts();

        System.out.println("\nSearching for Bob:");
        Node result = phoneBook.searchContact("9876543210");
        if (result != null) {
            System.out.println("Found: " + result.contactName);
        } else {
            System.out.println("Contact not found!");
        }

        System.out.println("\nUpdating Charlie's contact:");
        phoneBook.updateContact("5555555555", "Charlie Updated", "4444444444");
        phoneBook.displayContacts();

        System.out.println("\nDeleting Alice's contact:");
        phoneBook.deleteContact("1234567890");
        phoneBook.displayContacts();
    }
}
