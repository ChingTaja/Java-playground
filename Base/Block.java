package Base;

public class Block {
    /* 區域變數（local variables）」跟「作用域（scope） */
    public static void main(String[] args) {
        int x = 10;
        int y = 20;

        if (x > 5) {
            System.out.println(y);
        }
    }

    // 變數 一定在宣告它的區塊內
    // 也會在 內層（巢狀）區塊中
    // 外層或同層看不到
    public static void test(String[] args) {

        if (true) {
            int counter = 5;
        }

        // System.out.println(counter);  --> // 編譯錯誤

        int num = 2;
        switch (num) {
            case 2:
                int i = 10;
                break;
            default:
                i = num;
                System.out.println(i); // OK~
        }

        // System.out.println((i)); --> // 編譯錯誤 (out of scope)
    }

}
