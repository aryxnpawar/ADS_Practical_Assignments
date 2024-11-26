import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class HashTable{

    static class UserProfile{
        String name;
        ArrayList<String> posts;
        ArrayList<String> comments;

        HashMap<String,String> preferences;

        UserProfile(String name){
            this.name=name;
            this.posts=new ArrayList<>();
            this.comments=new ArrayList<>();
            this.preferences = new HashMap<>();
        }

        @Override
        public String toString() {
            return String.format("Name : %s,\nPosts : %s,\nComments : %s,\nPreferences : %s",name,posts,comments,preferences);
        }
    }

    private final int size = 7;
    private final Node[] dataMap;
    static class Node{
        String userID;  //unique
        UserProfile profile;
        Node next;

        public Node(String userID,UserProfile profile){
            this.userID=userID;
            this.profile=profile;
        }

    }
        public HashTable(){
            dataMap = new Node[size];
        }

        public void printHashTable(){
        for (int i =0;i<dataMap.length;i++){
            Node temp = dataMap[i];
            System.out.printf("%d : \n",i);
            while (temp!=null){
                System.out.printf("{ %s = %s}\n", temp.userID,temp.profile);
                temp = temp.next;
            }
        }
        }
        public int hash(String userID){
        int hashValue = 0;
            for (char c :
                    userID.toCharArray()) {
                int asciiValue = c;
                hashValue = (hashValue + asciiValue * 23) % dataMap.length;
            }
            return hashValue;
        }

    public void set(String userID, UserProfile profile) {
        Node newNode = new Node(userID, profile);
        int index = hash(userID);
        if (dataMap[index] == null) {
            dataMap[index] = newNode;
        } else {
            Node temp = dataMap[index];
            while (true) {
                if (temp.userID.equals(userID)) {
                    temp.profile = profile; // Update and exit
                    return;
                }
                if (temp.next == null) {
                    temp.next = newNode; // Append at the end
                    return;
                }
                temp = temp.next;
            }
        }
    }


    public UserProfile get(String userID){
        int index = hash(userID);
        Node temp = dataMap[index];
        while (temp!=null){
            if (userID.equals(temp.userID)) return temp.profile;
            temp = temp.next;
        }
        return null;
        }

        public ArrayList<String> keys(){
            ArrayList<String> allKeys = new ArrayList<>();
            Node temp;
            for (Node node : dataMap) {
                temp = node;
                while (temp != null) {
                    allKeys.add(temp.userID);
                    temp = temp.next;
                }
            }
            return allKeys;
        }

        public boolean contains(String userID){
        return get(userID)!=null;
        }

    public static void main(String[] args) {
        HashTable socialMediaApp = new HashTable();
        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.println("Welcome to our Social Media App!");

        while (true){
            System.out.println("Enter 1 to add/update a new user : ");
            System.out.println("Enter 2 display all users : ");
            System.out.println("Enter 3 to exit : ");

            if (!sc.hasNextInt()){
                System.out.println("Please enter a valid option!");
                sc.next();
                continue;
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice){
                case 1:
                    System.out.println("Enter a unique User id : ");
                    String UserID = sc.nextLine();
                    if(socialMediaApp.contains(UserID)){
                        System.out.println("User already Exists!");
                        System.out.println("Do you want to update the current user profile?\nEnter 1 to update and 0 if not");
                        int wantToUpdate = sc.nextInt();
                        sc.nextLine();
                        if(wantToUpdate==0)
                            break;
                    }
                    System.out.println("Please enter your new name :");
                    String newName = sc.nextLine();
                    UserProfile newUserProfile = new UserProfile(newName);

                    System.out.println("Enter 1 to add posts else to skip for now enter 0");
                    int wantToAddPosts = sc.nextInt();
                    sc.nextLine();

                    if (wantToAddPosts==1){
                        while (true){
                            System.out.println("Enter Post : ");
                            String newPost = sc.nextLine();
                            newUserProfile.posts.add(newPost);
                            System.out.println("Enter 1 to Continue else enter 0");
                            int continueAddingPosts = sc.nextInt();
                            sc.nextLine();
                            if(continueAddingPosts==0)
                                break;
                        }
                    }

                    //
                    System.out.println("Enter 1 to add Comments else to skip for now enter 0");
                    int wantToAddComments = sc.nextInt();
                    sc.nextLine();

                    if (wantToAddComments==1){
                        while (true){
                            System.out.println("Enter Comments : ");
                            String newComments = sc.nextLine();
                            newUserProfile.comments.add(newComments);
                            System.out.println("Enter 1 to Continue else enter 0");
                            int continueAddingComments = sc.nextInt();
                            sc.nextLine();
                            if(continueAddingComments==0)
                                break;
                        }
                    }

                    //
                    System.out.println("Enter 1 to add Preferences else to skip for now enter 0");
                    int wantToAddPreferences = sc.nextInt();
                    sc.nextLine();

                    if (wantToAddPreferences==1){
                        while (true){
                            System.out.println("Enter Preference (key) : ");
                            String newPreferenceKey = sc.nextLine();

                            System.out.printf("Describe your preference for %s (value): \n",newPreferenceKey);
                            String newPreferenceValue = sc.nextLine();

                            newUserProfile.preferences.put(newPreferenceKey,newPreferenceValue);

                            System.out.println("Enter 1 to Continue else enter 0");
                            int continueAddingPreferences = sc.nextInt();
                            sc.nextLine();
                            if(continueAddingPreferences==0)
                                break;
                        }
                    }

                    socialMediaApp.set(UserID,newUserProfile);
                    break;

                case 2:
                    socialMediaApp.printHashTable();
                    break;

                case 3:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Enter valid Option!");

        }
    }
}
}
