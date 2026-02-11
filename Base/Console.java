package Base;

import java.util.Scanner; // Scanner 不在預設套件

public class Console {
    public static void main(String[] args) {
        int currentYear = 2022;

        try {
            System.out.println(getInputFromConsole(currentYear));

            getInputFromScanner(currentYear);
        } catch (NullPointerException e) {
            System.out.println(getInputFromScanner(currentYear));
        }
    }

    /* System.console */
    public static String getInputFromConsole(int currentYear) {
        String name = System.console().readLine("Hi, What's your Name?");

        System.out.println("Hi" + name + ", Thanks for taking the course !");

        String dateOfBirth = System.console().readLine("What year were you born?");
        int age = currentYear - Integer.parseInt(dateOfBirth);

        return "So you are" + age + "years old";
    }

    /* Scanner Class */
    public static String getInputFromScanner(int currentYear) {
        // 讓你可以在主控台輸入資料，然後這些資料會被傳回程式中
        Scanner scanner = new Scanner(System.in);

        // String name = System.console().readLine("Hi, What's your Name?");

        System.out.println(("Hi, What's your name"));
        String name = scanner.nextLine(); // 讀取字串

        System.out.println("Hi" + name + ", Thanks for taking the course !");

        // String dateOfBirth = System.console().readLine("What year were you born?");

        boolean validDOB = false;
        int age = 0;

        do {
            System.out.println("Enter a year of birth >=" + (currentYear - 125) + "and <=" + (currentYear));
            try {
                age = checkData(currentYear, scanner.nextLine());
                validDOB = age < 0 ? false : true;
            } catch (NumberFormatException badUserData) {
                System.out.println("Characters not allowed!");
            }
        }while(!validDOB);

        return "So you are" + age + "years old";
    }

    public static int checkData(int currentYear, String dateOfBirth) {
        // check number
        int dob = Integer.parseInt(dateOfBirth);
        int minimumYear = currentYear - 125;

        if ((dob < minimumYear) || (dob > currentYear)) {
            return -1;
        }

        return (currentYear - dob);
    }
}

/*
 * Java 讀取 Console 輸入的幾種方式
 * 
 * Java 提供多種取得使用者輸入的方法，各有優缺點：
 * 
 * 🔹 System.in
 * 
 * 最底層的輸入方式
 * 
 * 使用困難、不直覺
 * 
 * 不適合初學者
 * 
 * 很多高階工具都是包在它外面
 * 
 * 🔹 System.console()
 * 
 * 提供較簡單的方式：讀取一整行輸入
 * 
 * 同時顯示 prompt 給使用者
 * 
 * 限制很大：
 * 
 * ❌ 在 IntelliJ、Eclipse 等 IDE 中無法使用
 * 
 * 只能在 真正的 terminal / command line 執行
 * 
 * 🔹 Command Line Arguments
 * 
 * 在執行 Java 程式時直接傳入參數
 * 
 * 常見、實用（特別是 server / script）
 * 
 * ❌ 不適合互動式程式
 * 
 * 使用者無法「被即時詢問」
 * 
 * 🔹 Scanner（重點）
 * 
 * 最常用、最適合初學者
 * 
 * 可以：從 System.in 讀取 console 輸入
 * 
 * 也可以讀取檔案 ✅ 可以在 IntelliJ 內直接執行
 * 
 */

/*
 * - Scanner 的兩種常見用法
 * 
 * 1. new Scanner(System.in); // 從 Console / Terminal 讀
 * 2. new Scanner(new File("data.txt")); // 從檔案讀
 * 
 * 
 */