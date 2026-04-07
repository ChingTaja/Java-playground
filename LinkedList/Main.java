package LinkedList;
import java.util.LinkedList;
import java.util.NoSuchElementException;



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

        String p2 = list.pollFirst();  // 與 poll() 完全一樣
        String p3 = list.pollLast(); // 移除並回傳 末尾 的元素

        list.push("Sydney");
        String p4 = list.pop(); // 移除並回傳堆疊的 頂端元素 (Top)。在 LinkedList 中，頂端就是 第一個元素。
        // 注意: 這與 poll() 不同，如果串列是空的，pop() 會丟出 NoSuchElementException 異常。poll()是回傳 null
    }
}
