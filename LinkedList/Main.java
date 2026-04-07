package LinkedList;
import java.util.LinkedList;
import java.util.ListIterator;



public class Main {
    public static void main(String[] args) {
        // LinkedList<String> placeToVisit = new LinkedList<>();
        var placeToVisit = new LinkedList<String>();

        placeToVisit.add("Sydney");
        placeToVisit.add(0, "Canberra");
        System.out.println(placeToVisit);

        addMoreElements(placeToVisit);
        removeElements(placeToVisit);
    }

    private static void addMoreElements(LinkedList<String> list) {
        list.addFirst("Darwin");
        list.addLast("Taja");
        // Queue methods
        list.offer("1"); // [1]
        list.offerFirst("2"); // [2, 1] 強制將元素加到串列的 開頭 (Head)
        list.offerLast("3"); // [2, 1, 3] 將元素加到串列的 末尾 (Tail)
        // Stack methods
        list.push("4"); // [4, 2, 1, 3] 將元素「推入」到串列的 開頭 (Head)
    }
    
    private static void removeElements(LinkedList<String> list) {
        list.remove(4);
        list.remove("Brisbane");

        System.out.println(list);
        String s1 = list.remove(); // remove first element
        System.out.println(s1 + " was removed");

        String s2 = list.removeFirst(); // remove first element
        System.out.println(s2 + " was removed");

        String s3 = list.removeLast(); // remove first element
        System.out.println(s3 + " was removed");

        // Queue/Deque poll methods
        String p1 = list.poll(); // 移除並回傳串列的 第一個元素 (Head)
        System.out.println(p1 + " was removed");

        String p2 = list.pollFirst(); // 與 poll() 完全一樣
        String p3 = list.pollLast(); // 移除並回傳 末尾 的元素

        list.push("Sydney");
        String p4 = list.pop(); // 移除並回傳堆疊的 頂端元素 (Top)。在 LinkedList 中，頂端就是 第一個元素。
        // 注意: 這與 poll() 不同，如果串列是空的，pop() 會丟出 NoSuchElementException 異常。poll()是回傳 null
    }
    
        private static void gettingElements(LinkedList<String> list) {

        System.out.println("Retrieved Element = " + list.get(4));

        System.out.println("First Element = " + list.getFirst());
        System.out.println("Last Element = " + list.getLast());

        System.out.println("Darwin is at position: " + list.indexOf("Darwin")); // 從頭尋找物件的索引

        System.out.println("Melbourne is at position: " +
                list.lastIndexOf("Melbourne"));
        
        // Queue retrieval method
        System.out.println("Element from element() = " + list.element()); //回傳 LinkedList 的第一個元素 (Head)

        // Stack retrieval methods
        System.out.println("Element from peek() = " + list.peek());  // 回傳第一個元素 (Head)
        System.out.println("Element from peekFirst() = " + list.peekFirst());
        System.out.println("Element from peekLast() = " + list.peekLast());
    }

    public static void printItinerary(LinkedList<String> list) {

        System.out.println("Trip starts at " + list.getFirst());
        // ❌ for + get(i) 這是沒效率的寫法
        // 為了拿第 0 個，走 0 步；拿第 1 個，走 1 步... 拿第 n 個，走 n/2 步。這會讓原本 O(n) 的遍歷變成 O(n^2)
        for (int i = 1; i < list.size(); i++) {
            // 每次迴圈都呼叫兩次 get()
            System.out.println("--> From: " + list.get(i - 1) + " to " + list.get(i));
        }
        System.out.println("Trip ends at " + list.getLast());
    }

    public static void printItinerary2(LinkedList<String> list) {

        System.out.println("Trip starts at " + list.getFirst());
        String previousTown = list.getFirst();
        // 第一輪輸出會是：From: A to A (重複第一個元素)
        for (String town : list) {
            System.out.println("--> From: " + previousTown + " to " + town);
            previousTown = town;
        }

        System.out.println("Trip ends at " + list.getLast());
    }

    // 解決了重複輸出的問題
    public static void printItinerary3(LinkedList<String> list) {

        System.out.println("Trip starts at " + list.getFirst());
        String previousTown = list.getFirst();
        // 迭代器指針直接跳過第 0 個元素，從索引 1 開始
        // ListIterator 的強大之處：
        // 可以指定起始位置：listIterator(index)。
        // 可以雙向移動：擁有 hasNext() / next() 以及 hasPrevious() / previous()。
        // 可以在遍歷過程中安全地新增或修改元素
        ListIterator<String> iterator = list.listIterator(1);
        while (iterator.hasNext()) {
            var town = iterator.next();
            System.out.println("--> From: " + previousTown + " to " + town);
            previousTown = town;
        }

        System.out.println("Trip ends at " + list.getLast());
    }
}
