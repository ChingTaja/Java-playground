package ArrayList;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ArrayAndArrayList {
    public static void main(String[] args) {
        String[] originalArray = new String[] { "First", "Second", "Thirdd" };
        var originalList = Arrays.asList(originalArray);

        originalList.set(0, "one");
        System.out.println(originalList);
        System.out.println(Arrays.toString(originalArray));

        originalList.sort(Comparator.naturalOrder());
        System.out.println(Arrays.toString(originalArray));

        // originalList.add("fourth");
        List<String> newList = Arrays.asList("Sunday", "Monday", "Tuesday");

        System.out.println(newList);
    }
}
