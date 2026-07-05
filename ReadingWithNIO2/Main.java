package ReadingWithNIO2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        /*
         * 取得並印出當前環境的字元編碼資訊
         * 包含 file.encoding 系統屬性與 Charset 的預設值
         */
        System.out.println(System.getProperty("file.encoding"));
        System.out.println(Charset.defaultCharset());

        Path path = Path.of("fixedWidth.txt");
        try {
            /*
             * Files.readAllBytes 會將整個檔案一次性讀入記憶體並回傳 byte array
             * 接著將其傳入 String 建構子轉換為文字呈現
             */
            System.out.println(new String(Files.readAllBytes(path)));
            System.out.println("----------------");
            /*
             * Files.readString 同樣是一次性讀取，但在處理純文字檔時更為安全、推薦
             * 內部會進行安全檢查與存取權限控管
             */
            System.out.println(Files.readString(path));

            /*
             * 定義固定寬度（Fixed width）文字檔的正規表示式 Pattern
             * 透過大括號指定每一欄位的精確字元長度
             */
            Pattern p = Pattern.compile("(.{15})(.{3})(.{12})(.{8})(.{2}).*");

            /*
             * 建立 TreeSet 容器
             * 利用 Set 元素不重複的特性達成資料的 distinct 效果，並依自然順序排序
             */
            Set<String> values = new TreeSet<>();

            /*
             * 使用傳統的 readAllLines 將檔案完整讀入 List<String> 集合
             * 並搭配迴圈進行特定欄位資料的解析
             */
            Files.readAllLines(path).forEach(s -> {
                /* 排除第一行的標題列（Header row） */
                if (!s.startsWith("Name")) {
                    Matcher m = p.matcher(s);
                    if (m.matches()) {
                        /* 擷取第三個群組（Group 3）的部門資訊並去除前後空白 */
                        values.add(m.group(3).trim());
                    }
                }
            });
            System.out.println(values);

            /*
             * 使用 Files.lines 串流方式處理
             * 因為 Stream 具備延遲執行的特性，底層資源必須被確實關閉
             * 因此必須強制包覆在 try-with-resources 語句中以防止資源洩漏
             */
            try (var stringStream = Files.lines(path)) {
                var results = stringStream
                        .skip(1) /* 跳過第一行的標題列 */
                        .map(p::matcher) /* 將每行字串轉換為 Matcher 實例 */
                        .filter(Matcher::matches) /* 篩選出符合正規表示式規格的資料 */
                        .map(m -> m.group(3).trim()) /* 擷取目標欄位並去除空白 */
                        .distinct() /* 排除重複資料 */
                        .sorted() /* 資料排序 */
                        .toArray(String[]::new); /* 收集並轉換為 String 陣列 */
                System.out.println(Arrays.toString(results));
            }

            /*
             * 再次透過 Files.lines 建立串流管道
             * 搭配終端操作（Terminal operation）進行進階的資料分組與統計聚合
             */
            try (var stringStream = Files.lines(path)) {
                var results = stringStream
                        .skip(1)
                        .map(p::matcher)
                        .filter(Matcher::matches)
                        /*
                         * 使用 Collectors.groupingBy 進行資料分組
                         * 第一個參數指定以部門名稱作為 Map 的 Key
                         * 第二個參數搭配 Collectors.counting() 統計各分組內的員工總數
                         */
                        .collect(Collectors.groupingBy(m -> m.group(3).trim(),
                                Collectors.counting()));

                /* 走訪並印出 Map 集合中的每一個 Entry 鍵值對 */
                results.entrySet().forEach(System.out::println);
            }
        } catch (IOException e) {
            /* 攔截 IO 異常並包裝改拋出執行時期異常 */
            throw new RuntimeException(e);
        }
    }
}