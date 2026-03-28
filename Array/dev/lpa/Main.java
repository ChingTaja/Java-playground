package Array.dev.lpa;

import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // 一旦陣列實例化之後，你就無法改變陣列的大小
        // 你不能增加或刪除元素
        // 在這個範例裡你只能對這 10 個元素中的其中一個進行賦值
        int[] myIntArray = new int[10];
        myIntArray[5] = 50;

        double[] myDoubleArray = new double[10];
        myDoubleArray[2] = 3.5;
        System.out.println(myDoubleArray[2]);

        int[] firstTen = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        System.out.println("first = " + firstTen[0]);
        int arrayLength = firstTen.length;
        System.out.println("length of array" + arrayLength);
        System.out.println("last" + firstTen[arrayLength - 1]);

        int[] newArray;
        // newArray = new int[] { 5, 4, 3, 2, 1 };
        newArray = new int[5];
        for (int i = 0; i < newArray.length; i++) {
            System.out.println(newArray[i] + " ");
        }

        for (int element : newArray) {
            System.out.println(element + " ");
        }
        System.out.println(newArray); // [I@@214c265e --> 非預期的數字
        System.out.println(Arrays.toString(newArray));
        // [5, 4, 3, 2, 1]

        Object objectVariable = newArray;
        if (objectVariable instanceof int[]) {
            System.out.println("objectVariable is really an int array");
        }

        Object[] objectArray = new Object[3];
        objectArray[0] = "Hello";
        objectArray[1] = new StringBuilder("world");
        objectArray[2] = newArray; // nested array

        // -----
        int[] firstArray = getRandomArray(10);
        System.out.println(Arrays.toString(firstArray));
        Arrays.sort(firstArray);
        System.out.println(Arrays.toString(firstArray));

        int[] secondArrray = new int[10];
        System.out.println(Arrays.toString(secondArrray));
        Arrays.fill(secondArrray, 5);
        System.out.println(Arrays.toString(secondArrray));

        int[] thirdArray = getRandomArray(10);
        System.out.println(Arrays.toString(thirdArray));


        int[] fourthArray = Arrays.copyOf(thirdArray, thirdArray.length);
        System.out.println(Arrays.toString(fourthArray));

        Arrays.sort(fourthArray);
        System.out.println(Arrays.toString(thirdArray));
        System.out.println(Arrays.toString(fourthArray)); // 不影響原陣列

        String[] sArray = { "Able", "Jane" };
        Arrays.sort(sArray);

        if (Arrays.binarySearch(sArray, "Taja") >= 0) {
            System.out.println("Found Mark in the list ");
        }

        int[] s1 = { 1, 2, 3, 4, 5 };
        int[] s2 = { 1, 2, 3, 4, 5 };

        // 同位置 , 同數值 , 同長度
        if (Arrays.equals(s1, s2)) {
            System.out.println("Arrays are equal");
        } else {
            System.out.println("Arrays are not equal");
        }            

    }
    
    public static int[] getRandomArray(int len) {
        Random random = new Random();
        int[] newInt = new int[len];

        for (int i = 0; i < len; i++) {
            newInt[i] = random.nextInt(100); // 回傳 0 到 99 之間的隨機整數
        }

        return newInt;
    }
 }
