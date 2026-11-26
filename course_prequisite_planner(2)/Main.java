
import java.util.*;

public class Main {

    // =========================================
    // 1. COURSE CLASS
    // =========================================

    static class Course {
        int id, credits, difficulty;
        String name;
        boolean completed;

        Course(int id, String name, int credits, int difficulty) {
            this.id = id;
            this.name = name;
            this.credits = credits;
            this.difficulty = difficulty;
            completed = false;
        }

        public String toString() {
            return id + " | " + name +
                    " | Credits: " + credits +
                    " | Difficulty: " + difficulty +
                    " | Completed: " + completed;
        }
    }

    // =========================================
    // 2. ARRAY IMPLEMENTATION
    // =========================================

    static class CourseArray {
        Course[] courses = new Course[100];
        int size = 0;

        void insert(Course c) {
            if (size == courses.length) {
                System.out.println("Array is full!");
                return;
            }

            courses[size++] = c;
        }

        int getSize() {
            return size;
        }

        Course get(int index) {
            if (index < 0 || index >= size)
                return null;

            return courses[index];
        }

        Course search(int id) {
            for (int i = 0; i < size; i++) {
                if (courses[i].id == id)
                    return courses[i];
            }
            return null;
        }

        void delete(int id) {
            for (int i = 0; i < size; i++) {
                if (courses[i].id == id) {
                    for (int j = i; j < size - 1; j++)
                        courses[j] = courses[j + 1];

                    courses[--size] = null;
                    System.out.println("Course deleted successfully!");
                    return;
                }
            }

            System.out.println("Course not found!");
        }

        void display() {
            if (size == 0) {
                System.out.println("No courses available.");
                return;
            }

            for (int i = 0; i < size; i++)
                System.out.println(courses[i]);
        }
    }

    // =========================================
    // 3. SINGLY LINKED LIST
    // =========================================

    static class SinglyLinkedList {

        class Node {
            Course data;
            Node next;

            Node(Course data) {
                this.data = data;
            }
        }

        Node head;

        void insert(Course c) {
            Node n = new Node(c);

            if (head == null) {
                head = n;
                return;
            }

            Node temp = head;

            while (temp.next != null)
                temp = temp.next;

            temp.next = n;
        }

        void display() {
            Node temp = head;

            while (temp != null) {
                System.out.println(temp.data.name);
                temp = temp.next;
            }
        }

        void delete(int id) {
            if (head == null) return;

            if (head.data.id == id) {
                head = head.next;
                return;
            }

            Node temp = head;

            while (temp.next != null) {
                if (temp.next.data.id == id) {
                    temp.next = temp.next.next;
                    return;
                }

                temp = temp.next;
            }
        }
    }

    // =========================================
    // 4. DOUBLY LINKED LIST
    // =========================================

    static class DoublyLinkedList {

        class Node {
            Course data;
            Node prev, next;

            Node(Course c) {
                data = c;
            }
        }

        Node head, tail;

        void insert(Course c) {
            Node n = new Node(c);

            if (head == null) {
                head = tail = n;
            } else {
                tail.next = n;
                n.prev = tail;
                tail = n;
            }
        }

        void display() {
            Node temp = head;

            while (temp != null) {
                System.out.println(temp.data.name);
                temp = temp.next;
            }
        }
    }

    // =========================================
    // 5. CIRCULAR LINKED LIST
    // =========================================

    static class CircularLinkedList {

        class Node {
            Course data;
            Node next;

            Node(Course c) {
                data = c;
            }
        }

        Node tail;

        void insert(Course c) {
            Node n = new Node(c);

            if (tail == null) {
                tail = n;
                n.next = n;
            } else {
                n.next = tail.next;
                tail.next = n;
                tail = n;
            }
        }

        void display() {
            if (tail == null) return;

            Node temp = tail.next;

            do {
                System.out.println(temp.data.name);
                temp = temp.next;
            } while (temp != tail.next);
        }
    }

    // =========================================
    // 6. STACK
    // =========================================

    static class Stack {
        int[] arr = new int[100];
        int top = -1;

        void push(int value) {
            if (top == arr.length - 1) {
                System.out.println("Stack Overflow");
                return;
            }

            arr[++top] = value;
        }

        int pop() {
            if (top == -1) {
                System.out.println("Stack Underflow");
                return -1;
            }

            return arr[top--];
        }

        int peek() {
            return top == -1 ? -1 : arr[top];
        }

        boolean isEmpty() {
            return top == -1;
        }
    }

    // =========================================
    // 7. CIRCULAR QUEUE
    // =========================================

    static class CircularQueue {
        int[] arr = new int[100];
        int front = 0, rear = -1, size = 0;

        void enqueue(int value) {
            if (size == arr.length) {
                System.out.println("Queue Full");
                return;
            }

            rear = (rear + 1) % arr.length;
            arr[rear] = value;
            size++;
        }

        int dequeue() {
            if (size == 0) {
                System.out.println("Queue Empty");
                return -1;
            }

            int value = arr[front];
            front = (front + 1) % arr.length;
            size--;

            return value;
        }

        boolean isEmpty() {
            return size == 0;
        }
    }

    // =========================================
    // 8. PRIORITY QUEUE
    // =========================================

    static class PriorityQueueDS {

        class Item {
            int value, priority;

            Item(int value, int priority) {
                this.value = value;
                this.priority = priority;
            }
        }

        ArrayList<Item> list = new ArrayList<>();

        void enqueue(int value, int priority) {
            list.add(new Item(value, priority));
        }

        int dequeue() {
            if (list.isEmpty()) return -1;

            int index = 0;

            for (int i = 1; i < list.size(); i++) {
                if (list.get(i).priority < list.get(index).priority)
                    index = i;
            }

            return list.remove(index).value;
        }
    }

    // =========================================
    // 9. DEQUE
    // =========================================

    static class DequeDS {
        ArrayDeque<Integer> deque = new ArrayDeque<>();

        void insertFront(int x) {
            deque.addFirst(x);
        }

        void insertRear(int x) {
            deque.addLast(x);
        }

        int deleteFront() {
            return deque.isEmpty() ? -1 : deque.removeFirst();
        }

        int deleteRear() {
            return deque.isEmpty() ? -1 : deque.removeLast();
        }
    }

    // =========================================
    // 10. COURSE GRAPH
    // =========================================

    static class CourseGraph {
        int vertices;
        ArrayList<Integer>[] adj;

        @SuppressWarnings("unchecked")
        CourseGraph(int v) {
            vertices = v;
            adj = new ArrayList[v];

            for (int i = 0; i < v; i++)
                adj[i] = new ArrayList<>();
        }

        void addEdge(int from, int to) {
            if (from < 0 || from >= vertices ||
                    to < 0 || to >= vertices) {
                System.out.println("Invalid vertex!");
                return;
            }

            adj[from].add(to);
        }

        // BFS
        void BFS(int start) {
            if (start < 0 || start >= vertices) return;

            boolean[] visited = new boolean[vertices];
            CircularQueue q = new CircularQueue();

            visited[start] = true;
            q.enqueue(start);

            System.out.print("BFS: ");

            while (!q.isEmpty()) {
                int v = q.dequeue();
                System.out.print(v + " ");

                for (int next : adj[v]) {
                    if (!visited[next]) {
                        visited[next] = true;
                        q.enqueue(next);
                    }
                }
            }

            System.out.println();
        }

        // DFS
        void DFS(int start) {
            if (start < 0 || start >= vertices) return;

            boolean[] visited = new boolean[vertices];

            System.out.print("DFS: ");
            dfsHelper(start, visited);
            System.out.println();
        }

        void dfsHelper(int v, boolean[] visited) {
            visited[v] = true;
            System.out.print(v + " ");

            for (int next : adj[v]) {
                if (!visited[next])
                    dfsHelper(next, visited);
            }
        }

        // Cycle Detection
        boolean hasCycle() {
            boolean[] visited = new boolean[vertices];
            boolean[] path = new boolean[vertices];

            for (int i = 0; i < vertices; i++) {
                if (!visited[i] && cycleDFS(i, visited, path))
                    return true;
            }

            return false;
        }

        boolean cycleDFS(int v, boolean[] visited, boolean[] path) {
            visited[v] = true;
            path[v] = true;

            for (int next : adj[v]) {
                if (!visited[next]) {
                    if (cycleDFS(next, visited, path))
                        return true;
                } else if (path[next]) {
                    return true;
                }
            }

            path[v] = false;
            return false;
        }

        // Topological Sort
        void topologicalSort() {
            int[] indegree = new int[vertices];

            for (int i = 0; i < vertices; i++) {
                for (int next : adj[i])
                    indegree[next]++;
            }

            CircularQueue q = new CircularQueue();

            for (int i = 0; i < vertices; i++) {
                if (indegree[i] == 0)
                    q.enqueue(i);
            }

            int count = 0;

            System.out.print("Course Learning Order: ");

            while (!q.isEmpty()) {
                int v = q.dequeue();
                System.out.print(v + " ");
                count++;

                for (int next : adj[v]) {
                    indegree[next]--;

                    if (indegree[next] == 0)
                        q.enqueue(next);
                }
            }

            if (count != vertices)
                System.out.println("\nCycle detected! No valid learning order.");
            else
                System.out.println();
        }

        // Bipartite Graph Checking (Undirected graph)
        boolean isBipartite() {
            int[] color = new int[vertices];
            Arrays.fill(color, -1);

            for (int start = 0; start < vertices; start++) {
                if (color[start] != -1) continue;

                CircularQueue q = new CircularQueue();
                q.enqueue(start);
                color[start] = 0;

                while (!q.isEmpty()) {
                    int v = q.dequeue();

                    for (int next : adj[v]) {
                        if (color[next] == -1) {
                            color[next] = 1 - color[v];
                            q.enqueue(next);
                        } else if (color[next] == color[v]) {
                            return false;
                        }
                    }
                }
            }

            return true;
        }
    }

    // =========================================
    // 11. BINARY SEARCH TREE
    // =========================================

    static class BST {

        class Node {
            int data;
            Node left, right;

            Node(int data) {
                this.data = data;
            }
        }

        Node root;

        Node insert(Node root, int value) {
            if (root == null)
                return new Node(value);

            if (value < root.data)
                root.left = insert(root.left, value);
            else if (value > root.data)
                root.right = insert(root.right, value);

            return root;
        }

        void add(int value) {
            root = insert(root, value);
        }

        boolean search(Node root, int value) {
            if (root == null) return false;

            if (root.data == value) return true;

            if (value < root.data)
                return search(root.left, value);

            return search(root.right, value);
        }

        void inorder(Node root) {
            if (root != null) {
                inorder(root.left);
                System.out.print(root.data + " ");
                inorder(root.right);
            }
        }
    }

    // =========================================
    // 12. SEARCHING
    // =========================================

    static class Searching {

        static int linearSearch(int[] a, int key) {
            for (int i = 0; i < a.length; i++) {
                if (a[i] == key)
                    return i;
            }

            return -1;
        }

        static int binarySearch(int[] a, int key) {
            int low = 0, high = a.length - 1;

            while (low <= high) {
                int mid = low + (high - low) / 2;

                if (a[mid] == key)
                    return mid;

                if (a[mid] < key)
                    low = mid + 1;
                else
                    high = mid - 1;
            }

            return -1;
        }
    }

    // =========================================
    // 13. SORTING
    // =========================================

    static class Sorting {

        static void swap(int[] a, int i, int j) {
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }

        static void bubbleSort(int[] a) {
            for (int i = 0; i < a.length - 1; i++)
                for (int j = 0; j < a.length - i - 1; j++)
                    if (a[j] > a[j + 1])
                        swap(a, j, j + 1);
        }

        static void selectionSort(int[] a) {
            for (int i = 0; i < a.length - 1; i++) {
                int min = i;

                for (int j = i + 1; j < a.length; j++)
                    if (a[j] < a[min])
                        min = j;

                swap(a, i, min);
            }
        }

        static void insertionSort(int[] a) {
            for (int i = 1; i < a.length; i++) {
                int key = a[i];
                int j = i - 1;

                while (j >= 0 && a[j] > key) {
                    a[j + 1] = a[j];
                    j--;
                }

                a[j + 1] = key;
            }
        }

        static void mergeSort(int[] a, int l, int r) {
            if (l >= r) return;

            int mid = l + (r - l) / 2;

            mergeSort(a, l, mid);
            mergeSort(a, mid + 1, r);

            int[] temp = new int[r - l + 1];
            int i = l, j = mid + 1, k = 0;

            while (i <= mid && j <= r) {
                if (a[i] <= a[j])
                    temp[k++] = a[i++];
                else
                    temp[k++] = a[j++];
            }

            while (i <= mid)
                temp[k++] = a[i++];

            while (j <= r)
                temp[k++] = a[j++];

            for (i = 0; i < temp.length; i++)
                a[l + i] = temp[i];
        }

        static void quickSort(int[] a, int low, int high) {
            if (low < high) {
                int pivot = partition(a, low, high);
                quickSort(a, low, pivot - 1);
                quickSort(a, pivot + 1, high);
            }
        }

        static int partition(int[] a, int low, int high) {
            int pivot = a[high];
            int i = low - 1;

            for (int j = low; j < high; j++) {
                if (a[j] <= pivot)
                    swap(a, ++i, j);
            }

            swap(a, i + 1, high);
            return i + 1;
        }

        static void heapSort(int[] a) {
            int n = a.length;

            for (int i = n / 2 - 1; i >= 0; i--)
                heapify(a, n, i);

            for (int i = n - 1; i > 0; i--) {
                swap(a, 0, i);
                heapify(a, i, 0);
            }
        }

        static void heapify(int[] a, int n, int i) {
            int largest = i;
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < n && a[left] > a[largest])
                largest = left;

            if (right < n && a[right] > a[largest])
                largest = right;

            if (largest != i) {
                swap(a, i, largest);
                heapify(a, n, largest);
            }
        }

        static void bucketSort(int[] a) {
            if (a.length == 0) return;

            int max = a[0];

            for (int x : a) {
                if (x < 0)
                    throw new IllegalArgumentException(
                            "Only non-negative numbers supported");

                max = Math.max(max, x);
            }

            int[] bucket = new int[max + 1];

            for (int x : a)
                bucket[x]++;

            int k = 0;

            for (int i = 0; i < bucket.length; i++) {
                while (bucket[i] > 0) {
                    a[k++] = i;
                    bucket[i]--;
                }
            }
        }
    }

    // =========================================
    // 14. HASHING WITH CHAINING
    // =========================================

    static class Hashing {

        static class Node {
            int key;
            Node next;

            Node(int key) {
                this.key = key;
            }
        }

        Node[] table = new Node[10];

        void insert(int key) {
            int index = Math.floorMod(key, table.length);

            Node n = new Node(key);
            n.next = table[index];
            table[index] = n;
        }

        boolean search(int key) {
            int index = Math.floorMod(key, table.length);

            Node temp = table[index];

            while (temp != null) {
                if (temp.key == key)
                    return true;

                temp = temp.next;
            }

            return false;
        }

        void display() {
            for (int i = 0; i < table.length; i++) {
                System.out.print(i + ": ");

                Node temp = table[i];

                while (temp != null) {
                    System.out.print(temp.key + " -> ");
                    temp = temp.next;
                }

                System.out.println("NULL");
            }
        }
    }

    // =========================================
    // 15. SPARSE MATRIX
    // =========================================

    static void sparseMatrix(int[][] matrix) {
        System.out.println("Row Column Value");

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != 0)
                    System.out.println(i + "     " + j +
                            "     " + matrix[i][j]);
            }
        }
    }

    // =========================================
    // 16. POLYNOMIAL REPRESENTATION
    // =========================================

    static void polynomial(int[] coefficients) {
        for (int i = 0; i < coefficients.length; i++) {
            int power = coefficients.length - i - 1;

            if (coefficients[i] != 0) {
                if (i > 0)
                    System.out.print(" + ");

                System.out.print(coefficients[i]);

                if (power > 0)
                    System.out.print("x");

                if (power > 1)
                    System.out.print("^" + power);
            }
        }

        System.out.println();
    }

    // =========================================
    // 17. ARRAY ADDRESS CALCULATION
    // =========================================

    static int rowMajor(int base, int i, int j,
                        int columns, int size) {
        return base + ((i * columns) + j) * size;
    }

    static int columnMajor(int base, int i, int j,
                           int rows, int size) {
        return base + ((j * rows) + i) * size;
    }

    // =========================================
    // 18. PARENTHESIS MATCHING
    // =========================================

    static boolean parenthesisMatching(String expression) {
        Stack stack = new Stack();

        for (char ch : expression.toCharArray()) {
            if (ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch);
            } else if (ch == ')' || ch == '}' || ch == ']') {
                if (stack.isEmpty())
                    return false;

                char open = (char) stack.pop();

                if ((ch == ')' && open != '(') ||
                    (ch == '}' && open != '{') ||
                    (ch == ']' && open != '['))
                    return false;
            }
        }

        return stack.isEmpty();
    }

    // =========================================
    // 19. INFIX TO POSTFIX
    // =========================================

    static int precedence(char ch) {
        if (ch == '^') return 3;
        if (ch == '*' || ch == '/') return 2;
        if (ch == '+' || ch == '-') return 1;
        return -1;
    }

    static String infixToPostfix(String expression) {
        Stack stack = new Stack();
        StringBuilder result = new StringBuilder();

        for (char ch : expression.toCharArray()) {

            if (Character.isLetterOrDigit(ch)) {
                result.append(ch);
            } else if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(')
                    result.append((char) stack.pop());

                if (!stack.isEmpty())
                    stack.pop();
            } else if (ch == '+' || ch == '-' ||
                       ch == '*' || ch == '/' || ch == '^') {

                while (!stack.isEmpty() && stack.peek() != '(' &&
                        (precedence((char) stack.peek()) > precedence(ch) ||
                        (precedence((char) stack.peek()) == precedence(ch)
                                && ch != '^'))) {
                    result.append((char) stack.pop());
                }

                stack.push(ch);
            }
        }

        while (!stack.isEmpty())
            result.append((char) stack.pop());

        return result.toString();
    }

    // =========================================
    // 20. MAIN METHOD
    // =========================================

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        CourseArray courses = new CourseArray();

        courses.insert(new Course(101, "Programming", 4, 2));
        courses.insert(new Course(102, "Data Structures", 4, 3));
        courses.insert(new Course(103, "Trees", 3, 4));
        courses.insert(new Course(104, "Graphs", 3, 4));
        courses.insert(new Course(105, "Advanced Algorithms", 4, 5));

        CourseGraph graph = new CourseGraph(5);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 4);

        int choice;

        do {
            System.out.println("\n====================================");
            System.out.println("     COURSE PREREQUISITE PLANNER");
            System.out.println("====================================");

            System.out.println("1. Display All Courses");
            System.out.println("2. Add Course");
            System.out.println("3. Search Course");
            System.out.println("4. Delete Course");
            System.out.println("5. BFS Traversal");
            System.out.println("6. DFS Traversal");
            System.out.println("7. Detect Cycle");
            System.out.println("8. Generate Learning Order");
            System.out.println("9. Check Bipartite Graph");
            System.out.println("10. Sorting Demonstration");
            System.out.println("11. Searching Demonstration");
            System.out.println("12. BST Demonstration");
            System.out.println("13. Hashing Demonstration");
            System.out.println("14. Sparse Matrix");
            System.out.println("15. Polynomial Representation");
            System.out.println("16. Array Address Calculation");
            System.out.println("17. Linked List Demonstration");
            System.out.println("18. Stack and Queue");
            System.out.println("19. Priority Queue and Deque");
            System.out.println("20. Parenthesis Matching");
            System.out.println("21. Infix to Postfix");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    courses.display();
                    break;

                case 2:
                    System.out.print("Enter Course ID: ");
                    int id = sc.nextInt();

                    sc.nextLine();

                    System.out.print("Enter Course Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Credits: ");
                    int credits = sc.nextInt();

                    System.out.print("Enter Difficulty (1-5): ");
                    int difficulty = sc.nextInt();

                    if (courses.search(id) != null) {
                        System.out.println("Course ID already exists!");
                    } else if (credits <= 0 ||
                            difficulty < 1 || difficulty > 5 ||
                            name.trim().isEmpty()) {
                        System.out.println("Invalid course details!");
                    } else {
                        courses.insert(
                                new Course(id, name, credits, difficulty));
                        System.out.println("Course Added Successfully!");
                    }
                    break;

                case 3:
                    System.out.print("Enter Course ID: ");
                    Course found = courses.search(sc.nextInt());

                    if (found == null)
                        System.out.println("Course Not Found");
                    else
                        System.out.println(found);

                    break;

                case 4:
                    System.out.print("Enter Course ID: ");
                    courses.delete(sc.nextInt());
                    break;

                case 5:
                    graph.BFS(0);
                    break;

                case 6:
                    graph.DFS(0);
                    break;

                case 7:
                    System.out.println("Cycle Detected: " +
                            graph.hasCycle());
                    break;

                case 8:
                    graph.topologicalSort();
                    break;

                case 9:
                    System.out.println("Bipartite: " +
                            graph.isBipartite());
                    break;

                case 10:
                    int[] arr = {50, 20, 80, 40, 10, 60};

                    System.out.println("Original: " +
                            Arrays.toString(arr));

                    Sorting.mergeSort(arr, 0, arr.length - 1);

                    System.out.println("Merge Sorted: " +
                            Arrays.toString(arr));

                    break;

                case 11:
                    int[] ids = {101, 102, 103, 104, 105};

                    System.out.print("Enter ID to search: ");
                    int key = sc.nextInt();

                    System.out.println("Linear Search Position: " +
                            Searching.linearSearch(ids, key));

                    System.out.println("Binary Search Position: " +
                            Searching.binarySearch(ids, key));

                    break;

                case 12:
                    BST bst = new BST();

                    bst.add(50);
                    bst.add(30);
                    bst.add(70);
                    bst.add(20);
                    bst.add(40);

                    System.out.print("BST Inorder: ");
                    bst.inorder(bst.root);

                    System.out.println();

                    System.out.println("Search 40: " +
                            bst.search(bst.root, 40));

                    break;

                case 13:
                    Hashing hashing = new Hashing();

                    hashing.insert(101);
                    hashing.insert(102);
                    hashing.insert(111);

                    hashing.display();

                    System.out.println("Search 102: " +
                            hashing.search(102));

                    break;

                case 14:
                    int[][] matrix = {
                            {0, 0, 5},
                            {0, 8, 0},
                            {3, 0, 0}
                    };

                    sparseMatrix(matrix);
                    break;

                case 15:
                    polynomial(new int[]{5, 4, 3});
                    break;

                case 16:
                    System.out.println("Row Major Address: " +
                            rowMajor(1000, 2, 3, 5, 4));

                    System.out.println("Column Major Address: " +
                            columnMajor(1000, 2, 3, 4, 4));

                    break;

                case 17:
                    SinglyLinkedList singly = new SinglyLinkedList();
                    DoublyLinkedList doubly = new DoublyLinkedList();
                    CircularLinkedList circular = new CircularLinkedList();

                    for (int i = 0; i < courses.getSize(); i++) {
                        singly.insert(courses.get(i));
                        doubly.insert(courses.get(i));
                        circular.insert(courses.get(i));
                    }

                    System.out.println("Singly Linked List:");
                    singly.display();

                    System.out.println("Doubly Linked List:");
                    doubly.display();

                    System.out.println("Circular Linked List:");
                    circular.display();

                    break;

                case 18:
                    Stack stack = new Stack();

                    stack.push(101);
                    stack.push(102);

                    System.out.println("Stack Pop: " + stack.pop());

                    CircularQueue queue = new CircularQueue();

                    queue.enqueue(101);
                    queue.enqueue(102);

                    System.out.println("Queue Dequeue: " +
                            queue.dequeue());

                    break;

                case 19:
                    PriorityQueueDS pq = new PriorityQueueDS();

                    pq.enqueue(101, 2);
                    pq.enqueue(102, 1);

                    System.out.println("Priority Queue Dequeue: " +
                            pq.dequeue());

                    DequeDS deque = new DequeDS();

                    deque.insertFront(100);
                    deque.insertRear(200);

                    System.out.println("Deque Front Delete: " +
                            deque.deleteFront());

                    System.out.println("Deque Rear Delete: " +
                            deque.deleteRear());

                    break;

                case 20:
                    sc.nextLine();

                    System.out.print("Enter Expression: ");
                    String expression = sc.nextLine();

                    System.out.println("Balanced: " +
                            parenthesisMatching(expression));

                    break;

                case 21:
                    sc.nextLine();

                    System.out.print("Enter Infix Expression: ");
                    String infix = sc.nextLine();

                    System.out.println("Postfix Expression: " +
                            infixToPostfix(infix));

                    break;

                case 0:
                    System.out.println(
                            "Thank You for Using Course Prerequisite Planner!");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 0);

        sc.close();
    }
}
