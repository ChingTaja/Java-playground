package ScannerProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        /*
         * 使用 try-with-resources 確保 Scanner 資源會被自動關閉
         * 若使用檔案作為資料來源，未關閉 Scanner 會導致底層檔案串流維持開啟狀態
         */
        try (Scanner scanner = new Scanner(
                new BufferedReader(new FileReader("fixedWidth.txt")))) {

            // /* 範例一：傳統的逐行讀取與列印 */
            // while (scanner.hasNextLine()) {
            // System.out.println(scanner.nextLine());
            // }

            // /* 範例二：檢查與修改預設分隔符號（Delimiter） */
            // /* 印出 Scanner 預設的分隔符號，預設為一或多個空白字元（包含換行符號） */
            // System.out.println(scanner.delimiter());
            // /* 將分隔符號修改為行尾錨點（$），達到逐行切分文字的目的 */
            // scanner.useDelimiter("$");
            // /* 使用 tokens() 方法將內容轉為 Stream 進行走訪列印 */
            // scanner.tokens().forEach(System.out::println);

            // /* 範例三：使用 findAll 搭配正規表示式篩選特定長度單字 */
            // scanner.findAll("[A-Za-z]{10,}")
            // /* 將 MatchResult 透過方法參考轉為符合匹配的字串 */
            // .map(MatchResult::group)
            // /* 排除重複的單字 */
            // .distinct()
            // /* 依字母順序進行自然排序 */
            // .sorted()
            // .forEach(System.out::println);

            /* 範例四：解析固定寬度（Fixed Width）文字檔案 */
            var results = scanner.findAll(
                    "(.{15})(.{3})(.{12})(.{8})(.{2}).*")
                    /* 跳過第一行（Header Row / 標頭欄位行） */
                    .skip(1)
                    /* 擷取第 3捕獲群組（Group 3，即部門欄位），並修剪掉因固定寬度產生的多餘空白 */
                    .map(m -> m.group(3).trim())
                    /* 排除重複的部門名稱 */
                    .distinct()
                    /* 依部門字母排序 */
                    .sorted()
                    /* 將 Stream 的結果收集並轉為字串陣列 */
                    .toArray(String[]::new);

            /* 一次性印出陣列內容 */
            System.out.println(Arrays.toString(results));

        } catch (IOException e) {
            /* 針對路徑或檔案存取可能拋出的 Checked Exception 進行捕捉與封裝 */
            throw new RuntimeException(e);
        }
    }
}