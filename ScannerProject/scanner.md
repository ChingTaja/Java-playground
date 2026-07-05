# scanner 的用法 + Scanner 背後的 IO/NIO 本質 + regex + stream 進階分析能力

#  1. Scanner 的特色

```java
Scanner scanner = new Scanner(new File("file.txt"));
```

### Scanner 可以接很多來源

- File
- Path
- String
- InputStream（如 System.in）
- ReadableByteChannel

重點：**Scanner 是「統一輸入處理工具」**

#  2. 一定要記得 close（try-with-resources）

```java
try (Scanner scanner = new Scanner(...)) {}
```

### 為什麼？

- File / Path 會開啟 OS resource
- 不 close = 檔案可能被鎖住

⚠️ 例外：

- `String` 不需要 close（沒有 IO）

# 3. System.in 的特殊性

- `System.in` = standard input stream
- JVM 全域唯一
- ❗不能 close（關掉就不能再用）

---

# 4. Scanner 基本讀法

```java
while (scanner.hasNextLine()) {   
     System.out.println(scanner.nextLine());
     }
```

### 行為：

- 一行一行讀
- 很直覺


#  5. tokens()（Stream 版本）


`tokens()` 是 **Scanner 在 Java 9 之後提供的一個方法**

用途：

>  把輸入資料「切成一個個 token（字詞）」並轉成 Stream 來處理

### default delimiter：

```
\\s+
```

 意思：

- 用空白 / 換行切字


### 改成「整行」：

```java
scanner.useDelimiter("$");
```

 `$` = 行結尾（regex）


# 6. tokens + Stream

```java
scanner.tokens().forEach(System.out::println);
```

### 好處：

- 可以用 Stream API
- filter / map / sort 都能做


#  7. findAll()

```java
scanner.findAll("regex")
```

### 回傳：

```java
Stream<MatchResult>
```

## Example行為

```java
scanner.findAll("\\b\\w{10,}\\b")
```

### 代表：

- 找「長度 ≥ 10 的單字」

### Stream 處理：

```java
.map(MatchResult::group).distinct().sorted()
```

會得到：
- 不重複
- 排序過
- 符合 regex 的結果


# 8. fixed-width file（固定欄位格式）

例如：

```
Name(15) Age(3) Dept(12) Salary(8) State(2)
```

## regex 解法：

```
(.{15})(.{3})(.{12})(.{8})(.{2}).*
```

### group 對應：

|group|意義|
|---|---|
|1|name|
|2|age|
|3|department|
|4|salary|
|5|state|

---

# 9. Stream 分析能力

### 常見操作：

```java
.distinct().sorted().skip(1)   // skip header
```

---

## trim

```java
.map(String::trim)
```

 因為 fixed-width 會有空白

# 10. Scanner vs Stream 結合能力

Scanner 不只是讀資料，它可以：

1. 讀 line  
2. 讀 token  
3. regex 搜尋  
4. stream pipeline

本質是：

> 「資料輸入 + 初步分析工具」

# 11. Scanner 背後其實是 NIO


### 如果用：

```java
new Scanner(Path)
```

內部會走：

- Files.newInputStream()
- NIO channel
- ReadableByteChannel


### Scanner
表面 IO class
實際 = NIO pipeline


# 12. File / Path / Reader 差異

| 來源            | 底層              |
| ------------- | ---------------- |
| File          | IO               |
| FileReader    | IO + buffer      |
| Path          | NIO              |
| Scanner(Path) | NIO (internally) |


# 13. Scanner + FileReader 差異

### FileReader：

- 更底層
- buffer 較小
- 控制力高

### Scanner：

- 更方便
- regex + parsing
- 適合分析資料


# 14. BufferedReader vs Scanner

| 功能          | BufferedReader | Scanner |
| ----------- | -------------- | ------- |
| line read   | ✔ 快            | ✔       |
| parsing     | ❌              | ✔✔✔     |
| regex       | ❌              | ✔✔✔     |
| performance |  fastest       | slower  |


## Scanner 的本質：

> Scanner = 「方便版資料分析工具 + IO wrapper」


## 三大能力：

### 1️. 讀資料

- file / path / inputstream

### 2️. 切資料

- delimiter
- regex

### 3. 分析資料

- stream pipeline
- findAll + MatchResult

> Scanner 不是單純讀檔工具，而是「可以直接做資料分析的 IO 工具」