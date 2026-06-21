package ResourceBundleProject;

import javax.swing.*;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main {

    public static void main(String[] args) {

        /*
         * 觀念：
         * 為了在不同語系環境下測試，使用增強型 for 迴圈
         * 依序遍歷美國英文（US）、加拿大法文（CANADA_FRENCH）與加拿大英文（CANADA）。
         */
        for (Locale l : List.of(Locale.US, Locale.CANADA_FRENCH,
                Locale.CANADA)) {

            /*
             * 透過基底名稱 BasicText 取得 ResourceBundle 實例
             * 當前環境未指定 Locale 時，預設會載入預設區域語系的屬性檔案
             */
            /*
             * 此處傳入迴圈變數 l 作為第二參數，讓 getBundle 依據不同 Locale 動態載入對應的 properties 檔案。
             */
            ResourceBundle rb = ResourceBundle.getBundle("BasicText", l);

            /* 印出實際回傳的類別名稱（通常為 java.util.PropertyResourceBundle） */
            // System.out.println(rb.getClass().getName());

            /* 印出此 ResourceBundle 的基底名稱（BasicText） */
            // System.out.println(rb.getBaseBundleName());

            /* 印出此 ResourceBundle 中包含的所有 key 集合 */
            // System.out.println(rb.keySet());

            /* 透過指定的 key（hello 與 world）取得對應的文字值，並格式化輸出 */
            /*
             * 💡 尋找與匹配演算法（Searching and Matching Algorithm）：
             * 以 Locale.CANADA (en_CA) 為例，當呼叫 rb.getString("hello") 時：
             * 1. 優先至 BasicText_en_CA.properties 尋找 ➔ 發現該檔案中 hello 欄位為空！
             * 2. 退一步尋找語言檔 BasicText_en.properties ➔ 發現系統中無此檔案。
             * 3. 自動回溯（Fallback）到基底檔案 BasicText.properties ➔ 成功找到 "hello" 並輸出。
             * 這種機制允許我們僅在特定區域檔案配置「有差異的字詞（如 world=whole earth）」，省去大量重複工作。
             */
            String message = "%s %s!%n".formatted(
                    rb.getString("hello"), rb.getString("world"));

            /*
             * Multiple Resource Bundles 應用：
             * 除了文字訊息外，系統可建立第二個資源包（如 UIComponents）專門打理介面元件（如按鈕標籤、視窗標題）
             * 註：properties 檔案語法包容性極高，除了等號（=），亦支援冒號（:）或空格來分隔 Key 與 Value，
             * 且註解除了 # 之外，也能使用驚嘆號（!）開頭。
             */
            ResourceBundle ui = ResourceBundle.getBundle("UIComponents", l);

            /*
             * 整合 Java 內建圖形介面工具包（Swing 套件）：
             * - 第一參數傳入 null：使對話視窗獨立且居中螢幕顯示。
             * - 第二參數 message：由 BasicText 資源包動態產生並格式化後的訊息文字。
             * - 第三參數 ui.getString("first.title")：由 UIComponents 提供的在地化視窗標題（法文下會呈現
             * "Première Application"）。
             * - 倒數第二參數（Object 陣列）：透過 rb 資源包動態抽換為當地的 yes/no 文字（例如：yes/no、oui/non、yep/no）。
             */
            JOptionPane.showOptionDialog(null,
                    message,
                    ui.getString("first.title"),
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[] { rb.getString("yes"), rb.getString("no") },
                    null);
        }
    }
}
