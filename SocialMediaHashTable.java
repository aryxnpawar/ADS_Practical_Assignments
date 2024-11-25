

import java.util.ArrayList;
import java.util.HashMap;

public class SocialMediaHashTable {

    private int size = 7;
    private Node[] dataMap;

    public SocialMediaHashTable() {
        dataMap = new Node[size];
    }

    public class Node {
        String username; // Key: Unique username for each user
        UserProfile profile; // Value: User profile data
        Node next;

        public Node(String username, UserProfile profile) {
            this.username = username;
            this.profile = profile;
            this.next = null;
        }
    }

    // Inner class to represent a user profile
    public static class UserProfile {
        String name;
        ArrayList<String> posts;
        ArrayList<String> comments;
        HashMap<String, String> preferences; // Example: theme, language

        public UserProfile(String name) {
            this.name = name;
            this.posts = new ArrayList<>();
            this.comments = new ArrayList<>();
            this.preferences = new HashMap<>();
        }

        public void addPost(String post) {
            posts.add(post);
        }

        public void addComment(String comment) {
            comments.add(comment);
        }

        public void setPreference(String key, String value) {
            preferences.put(key, value);
        }

        @Override
        public String toString() {
            return "UserProfile{" +
                    "name='" + name + '\'' +
                    ", posts=" + posts +
                    ", comments=" + comments +
                    ", preferences=" + preferences +
                    '}';
        }
    }

    private int hash(String key) {
        int hash = 0;
        char[] keyChars = key.toCharArray();

        for (int i = 0; i < keyChars.length; i++) {
            int ascii = keyChars[i];
            hash = (hash + ascii * 17) % dataMap.length;
        }
        return hash;
    }

    // Store or update a user profile
    public void set(String username, UserProfile profile) {
        int index = hash(username);
        Node newNode = new Node(username, profile);

        if (dataMap[index] == null) {
            dataMap[index] = newNode;
        } else {
            Node temp = dataMap[index];
            while (temp != null) {
                if (temp.username.equals(username)) {
                    temp.profile = profile; // Update existing user profile
                    return;
                }
                if (temp.next == null) break;
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }

    // Retrieve a user profile by username
    public UserProfile get(String username) {
        int index = hash(username);
        Node temp = dataMap[index];
        while (temp != null) {
            if (temp.username.equals(username)) {
                return temp.profile;
            }
            temp = temp.next;
        }
        return null; // Return null if user is not found
    }

    // Get a list of all usernames
    public ArrayList<String> keys() {
        ArrayList<String> allKeys = new ArrayList<>();
        for (int i = 0; i < dataMap.length; i++) {
            Node temp = dataMap[i];
            while (temp != null) {
                allKeys.add(temp.username);
                temp = temp.next;
            }
        }
        return allKeys;
    }

    // Print the entire hash table
    public void printTable() {
        for (int i = 0; i < dataMap.length; i++) {
            System.out.println(i + ": ");
            Node temp = dataMap[i];
            while (temp != null) {
                System.out.println("  {" + temp.username + " = " + temp.profile + "}");
                temp = temp.next;
            }
        }
    }

    // Main method to demonstrate the implementation
    public static void main(String[] args) {
        SocialMediaHashTable smHashTable = new SocialMediaHashTable();

        // Create user profiles
        UserProfile user1 = new UserProfile("Alice");
        user1.addPost("Hello World!");
        user1.addComment("Nice to meet everyone!");
        user1.setPreference("theme", "dark");

        UserProfile user2 = new UserProfile("Bob");
        user2.addPost("Java is great!");
        user2.setPreference("language", "English");

        UserProfile user3 = new UserProfile("Charlie");
        user3.addPost("Loving this platform!");
        user3.addComment("Awesome post, Alice!");

        // Store user profiles in the hash table
        smHashTable.set("alice123", user1);
        smHashTable.set("bob456", user2);
        smHashTable.set("charlie789", user3);

        // Retrieve and print user profiles
        System.out.println("Profile of alice123: " + smHashTable.get("alice123"));
        System.out.println("Profile of bob456: " + smHashTable.get("bob456"));

        // Print all usernames
        System.out.println("All usernames: " + smHashTable.keys());

        // Print the full hash table
        System.out.println("\nFull Hash Table:");
        smHashTable.printTable();
    }
}
