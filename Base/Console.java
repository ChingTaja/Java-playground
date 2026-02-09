package Base;

public class Console {
    public static void main(String[] args) {
        int currentYear = 2022;

        System.out.println(getInputFromConsole(currentYear));
        System.out.println(getInputFromScanner(currentYear));
    }

    public static String getInputFromConsole(int currentYear) {
        String name = System.console().readLine("Hi, What's your Name?");

        System.out.println("Hi" + name + ", Thanks for taking the course !");

        String dateOfBirth = System.console().readLine("What year were you born?");
        int age = currentYear - Integer.parseInt(dateOfBirth);

        return "So you are" + age + "years old";
    }

    public static String getInputFromScanner(int currentYear) {
        return "";
    }
}

/*
 * 
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