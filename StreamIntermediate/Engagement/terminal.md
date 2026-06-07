# Advanced Terminal Operation 

- `summaryStatistics()` 的限制與優勢** `summaryStatistics()` 是一個高效的數據摘要終端操作，但它無法直接運作在一般的泛型 `Stream<T>` 上，必須先透過 `mapToInt()`、`mapToDouble()` 或 `mapToLong()` 將串流轉型為基本型別專屬串流（如 `IntStream`），才能呼叫該方法並一舉獲得包含 `count`、`min`、`max`、`sum` 與 `average` 的綜合統計物件

`summaryStatistics()` 這個方法是定義在以下三種 primitive stream 上：

- `IntStream`
- `LongStream`
- `DoubleStream`

- 資料流水線的去重與排序（`distinct` 與 `sorted`）  `.map(Student::getCountryCode).distinct().sorted()`  是標準的資料清洗流程。其中 `distinct()` 能自動篩除串流中完全相同的重複元素（背後依賴物件的 `equals` 與 `hashCode` 機制），再結合 `sorted()` 操作，可以優雅地對資料進行常規排序
    
- 短路終端操作 `anyMatch` 與 `count` 的應用場景區隔 `anyMatch(...)` 接收一個 `Predicate`，屬於「短路操作」。它關心的是整組資料集對於該條件的「有無」，一旦在串流中找到第一個符合的元素，水管便會立刻關閉並直接回傳 `true`，效率極高；若需要知道具體有多少人，則必須將條件寫入中間操作 `filter` 中，再配合 `count()` 統計總數
    
- 多重過濾鏈結（Filter Chaining）的內部優化機制 在 Stream 中將多個篩選條件拆開成多個連續的 `.filter()` 語句，其最終執行效果與在單一個 `.filter()` 裡面用 `&&` 運算子串接完全相同。串流的內部流水線處理機制（Pipeline Processing）會自動判斷並安排最有效率的方式來執行這些篩選，因此完全可以依據程式碼的「可讀性」來決定如何切分 filter
    


- **時間間隔計算（`Period` 類別）**
    
    - 當需要計算兩個 `LocalDate` 之間相差幾年、幾個月、或幾天時，JDK 8 的 `Period.between(start, end)` 是最標準的工具
        
- Calculated Fields
    
    - 類別內部雖然持有複雜的 `Course` 實體和 `LocalDate` 物件，但透過 Getter 重新設計（如 `getCourseCode()` 回傳 `String`、`getLastActivityMonth()` 回傳簡寫字串），確保外部使用者不需要理解底層複雜的日期結構，就能輕鬆獲取所需的精簡資訊



- Defensive Copy
    
    - 當類別內部持有集合（如 `Map` 或 `List`）並對外提供 Getter 時，若直接回傳物件參照，外部就能繞過封裝直接調用 `.clear()` 或 `.put()` 竄改內部資料
        
    - 透過 **`Map.copyOf(engagementMap)`**，Java 會建立一個不可修改（Unmodifiable）的唯讀複本，徹底杜絕外部的惡意或無意修改，這是撰寫安全 Java 程式碼的黃金準則。
        
- 時間邊界安全機制（Date Border Control）
    
    - 在自動化數據生成器（如 `getRandomStudent`）中，隨機生成複合欄位（入學年與活動月）時，非常容易產生邏輯衝突（例如：學生 2020 年才入學，卻在 2018 年有看課紀錄；或是產生了 2026 年 12 月的未來看課紀錄）。
        
    - 程式中透過 `student.getYearEnrolled()` 與 `LocalDate.now().getMonthValue()` 進行天花板與地板邊界限縮，確保測試資料的邏輯絕對真實合理。
        
- 利用 `Stream.generate()` 與「供應者（Supplier）」大量產製測試集
    
    - 在 `Main.java` 中使用的 `Stream.generate(() -> Student.getRandomStudent(jmc, pymc))` 是一個無窮流。
        
    - 傳遞給它的 Lambda 運算式本質上是一個 **`Supplier`（不接收參數，但負責產出物件）**。儘管介面規範不收參，但我們依然可以在 Lambda 內部調用靜態工廠方法並傳入基礎變數（如 `jmc, pymc`），最後利用 `.limit(10)` 來輕鬆獲取 10 筆兼具高度隨機性與多樣性的完整物件網絡，這在 Stream 管道測試中是極其強大的技巧。