###  高階方法（推薦）

```java
Files.readAllLines()
```

- 一行搞定
- 適合大多數情況
- 但「封裝太多」，看不到底層原理

---

### 低階方法（理解原理）

- `FileReader`
- `FileInputStream`
- `InputStream`

> 用來理解「檔案怎麼被讀出來」

---

# 2. FileReader 的行為

```java
int data = reader.read();
```

### 重要觀念：

- 回傳的是 `int`
- 不是 char
- -1 = EOF（檔案結束）


### 為什麼會看到 48–57？

因為讀到的是 ASCII：

- `'0' → 48`
- `'9' → 57`

> Java 讀的是「數字編碼」，不是字

---

#  3. char 轉換

```
(char) data
```

把 ASCII 轉回人類可讀文字


#  4. 問題：太慢（效能差）

### ❌ 一次讀一個字元

- 每次可能觸發 disk read
- disk read = 很慢（機械/硬體操作）

#  Buffer 是什麼？

> 記憶體中的暫存區

### 作用：

- 一次從磁碟讀很多資料
- 存進 RAM
- 再慢慢用 RAM 取資料

### 結果

- 減少 disk IO 次數
- 大幅提升效能

#  6. FileReader 的特性

- 內建 buffer
- 但大小「不可控制」
- buffer size 由 JVM / OS 決定

# 7. 一次讀多個字元（更快）

```java
char[] buffer = new char[1000];
reader.read(buffer);
```

### 優點：

- 一次讀一批資料
- 減少 IO 次數

# 8. InputStream 概念

### InputStream 是：

>「byte 流」的抽象概念

### FileInputStream

- 讀 binary（圖片、檔案原始資料）
- 非常底層

### 問題：

- `.read()` 很慢（每次 disk IO）

### 解法：

```
BufferedInputStream
```

 加 buffer 提升效率

---

#  9. Reader vs InputStream

|類型|處理內容|
|---|---|
|InputStream|bytes|
|Reader|characters|

---

### 重要橋樑：

```
InputStreamReader
```

> bytes → characters


# 10. BufferedReader

```java
BufferedReader br = new BufferedReader(new FileReader("file.txt"));
```

### 優點：

- 更大的 buffer
- 提供 `readLine()`


### 用法：

```java
String line = br.readLine();
```

一次讀一整行

#  11. Java 8 + Stream 寫法（現代用法）

```java
br.lines().forEach(System.out::println);
```

### 好處：

- 直接變 Stream
- 可以 filter / map / transform


### 例子：

```java
br.lines()  
    .filter(l -> l.contains("Java"))  
    .forEach(System.out::println);
```

# 12. Scanner

Scanner：

- 更方便解析（int, double, token）
- 功能更高層
- 但通常比 BufferedReader 慢