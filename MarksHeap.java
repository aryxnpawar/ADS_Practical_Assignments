import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class MinHeap {
    private List<Float> heap;

    public MinHeap() {
        this.heap = new ArrayList<>();
    }

    public void insert(float value) {
        heap.add(value);
        int currentIndex = heap.size() - 1;
        while (currentIndex > 0 && heap.get(parent(currentIndex)) > heap.get(currentIndex)) {
            swap(parent(currentIndex), currentIndex);
            currentIndex = parent(currentIndex);
        }
    }

    public Float remove() {
        if (heap.isEmpty()) return null;
        if (heap.size() == 1) return heap.remove(0);

        float min = heap.get(0);
        heap.set(0, heap.remove(heap.size() - 1));
        sinkDown(0);
        return min;
    }

    public Float peek() {
        return heap.isEmpty() ? null : heap.get(0);
    }

    private int leftChild(int index) {
        return 2 * index + 1;
    }

    private int rightChild(int index) {
        return 2 * index + 2;
    }

    private int parent(int index) {
        return (index - 1) / 2;
    }

    private void swap(int index1, int index2) {
        float temp = heap.get(index1);
        heap.set(index1, heap.get(index2));
        heap.set(index2, temp);
    }

    private void sinkDown(int index) {
        int minIndex = index;
        while (true) {
            int leftIndex = leftChild(index);
            int rightIndex = rightChild(index);

            if (leftIndex < heap.size() && heap.get(leftIndex) < heap.get(minIndex))
                minIndex = leftIndex;
            if (rightIndex < heap.size() && heap.get(rightIndex) < heap.get(minIndex))
                minIndex = rightIndex;

            if (minIndex != index) {
                swap(index, minIndex);
                index = minIndex;
            } else {
                break;
            }
        }
    }
}

class MaxHeap {
    private List<Float> heap;

    public MaxHeap() {
        this.heap = new ArrayList<>();
    }

    public void insert(float value) {
        heap.add(value);
        int currentIndex = heap.size() - 1;
        while (currentIndex > 0 && heap.get(parent(currentIndex)) < heap.get(currentIndex)) {
            swap(parent(currentIndex), currentIndex);
            currentIndex = parent(currentIndex);
        }
    }

    public Float remove() {
        if (heap.isEmpty()) return null;
        if (heap.size() == 1) return heap.remove(0);

        float max = heap.get(0);
        heap.set(0, heap.remove(heap.size() - 1));
        sinkDown(0);
        return max;
    }

    public Float peek() {
        return heap.isEmpty() ? null : heap.get(0);
    }

    private int leftChild(int index) {
        return 2 * index + 1;
    }

    private int rightChild(int index) {
        return 2 * index + 2;
    }

    private int parent(int index) {
        return (index - 1) / 2;
    }

    private void swap(int index1, int index2) {
        float temp = heap.get(index1);
        heap.set(index1, heap.get(index2));
        heap.set(index2, temp);
    }

    private void sinkDown(int index) {
        int maxIndex = index;
        while (true) {
            int leftIndex = leftChild(index);
            int rightIndex = rightChild(index);

            if (leftIndex < heap.size() && heap.get(leftIndex) > heap.get(maxIndex))
                maxIndex = leftIndex;
            if (rightIndex < heap.size() && heap.get(rightIndex) > heap.get(maxIndex))
                maxIndex = rightIndex;

            if (maxIndex != index) {
                swap(index, maxIndex);
                index = maxIndex;
            } else {
                break;
            }
        }
    }
}

public class MarksHeap {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        MaxHeap maxHeap = new MaxHeap();
        MinHeap minHeap = new MinHeap();

        System.out.println("Welcome to Marks Display System!");
        System.out.println("Enter number of students : ");
        int n = sc.nextInt();
        sc.nextLine();
        float[] arrayOfMarks = new float[n];
        while (true){

            System.out.println("Enter 1 to add students :");
            System.out.println("Enter 2 to see Max and Min Marks : ");
            System.out.println("Enter 3 to exit");
            choice = sc.nextInt();;
            sc.nextLine();

            switch (choice){
                case 1:
                    for (int i=0;i<n;i++){
                        System.out.printf("Enter Marks of Student %d : \n",i+1);
                        float marks = sc.nextFloat();
                        sc.nextLine();
                        minHeap.insert(marks);
                        maxHeap.insert(marks);
                        arrayOfMarks[i] = marks;
                    }
                    break;
                case 2:
                    if (minHeap.peek()==null || maxHeap.peek()==null){
                        System.out.println("Empty List");
                    }
                    else {
                        float minMarks = minHeap.peek();
                        float maxMarks = maxHeap.peek();
                        System.out.printf("Min Marks : %.2f\n", minMarks);
                        System.out.printf("Max Marks : %.2f\n", maxMarks);
                    }
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Enter Valid choice");

            }

        }
    }

//    public static void main(String[] args) {
//        // Sample marks data
//        int[] marks = {85, 92, 78, 99, 64, 88, 71, 95, 84, 76};
//
//        MinHeap minHeap = new MinHeap();
//        MaxHeap maxHeap = new MaxHeap();
//
//        // Insert all marks into both heaps
//        for (int mark : marks) {
//            minHeap.insert(mark);
//            maxHeap.insert(mark);
//        }
//
//        // Get the minimum and maximum marks
//        int minMarks = minHeap.peek();
//        int maxMarks = maxHeap.peek();
//
//        // Output the results
//        System.out.println("Minimum marks obtained: " + minMarks);
//        System.out.println("Maximum marks obtained: " + maxMarks);
//    }
}
