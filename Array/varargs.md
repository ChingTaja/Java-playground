1. 什麼是 Varargs？
如果你希望一個方法能接受「不確定數量」的參數
可以使用 ...（三個點）來宣告
語法： `Type... parameterName` (例如 String... args)
Java 在幕後會將這些傳入的參數自動封裝成一個陣列

2. 宣告方式與 main 方法
熟悉的 main 方法其實可以寫成兩種形式，效果完全一樣
標準版：`public static void main(String[] args)`
Varargs 版：`public static void main(String... args)`

3. 使用 Varargs 的優勢它讓呼叫方法變得極其靈活
可以傳入：
- 一個陣列：printText(new String[]{"A", "B"})
- 單個元素：printText("Hello")
- 多個元素（用逗號隔開）：printText("A", "B", "C")
- 完全不傳參數：printText() (此時會產生一個長度為 0 的陣列)

⚠️ Varargs 的使用限制 
為了避免編譯器混淆，使用時必須遵守兩大規則：
- 只能有一個：一個方法中最多只能有一個可變參數
- 必須放在最後：可變參數必須是參數清單中的最後一個
  - 正確：`public void test(int a, String... b)`
  - 錯誤：`public void test(String... b, int a)`
  



  
補充：String.split()與先前學過的 String.join() 相反
split 是將字串「拆解」成陣列
功能： 根據指定的「分隔符號」（Delimiter）切開字串。範例：JavaString str = "Hello World Again";
String[] array = str.split(" "); // 以空白字元作為切割點
// 結果 array = {"Hello", "World", "Again"}
📊 綜合比較：String[] vs String...特性一般陣列參數 (String[])可變參數 (String...)呼叫時必須傳入一個陣列物件。可傳陣列、單一值、多值或不傳。靈活性較低，呼叫端較麻煩。極高，呼叫端代碼簡潔。方法體內當作陣列處理。同樣當作陣列處理。限制無位置限制。必須位於參數列最後一位。💡 老師的開發建議何時使用 Varargs？ 當你設計一個方法，但不確定使用者會傳入多少個資料項時（例如：打印訊息、加總數字），Varargs 是最佳選擇。String.join 的秘密：String.join(CharSequence delimiter, CharSequence... elements) 也是利用 Varargs，這就是為什麼我們可以傳入一個陣列，也可以直接傳入好幾個字串。