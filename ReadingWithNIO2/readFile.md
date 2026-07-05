# Java NIO.2（Files + Charset + Stream）讀檔的「現代完整用法」


# 1. 字元編碼（Charset）

> 電腦其實只懂「bytes」，不是文字

文字 = 編碼後的結果

##  常見編碼

|編碼|特性|
|---|---|
|ASCII|只英文（7-bit）|
|ISO-8859-1|擴展 ASCII（8-bit）|
|Unicode|全球字元系統|
|UTF-8|最常用（可變長度）|
|UTF-16|固定 2 bytes|
|UTF-32|固定 4 bytes（很浪費空間）|


## Java 預設

```java
Charset.defaultCharset()
```

或

```java
System.getProperty("file.encoding")
```

 通常是 **UTF-8**

# 2. 三種「一次讀完整檔案」方法（NIO.2）

##  readAllBytes（最低層）

```java
byte[] bytes = Files.readAllBytes(path);
String text = new String(bytes);
```

### 特色：

- 讀「整個檔案 → byte[]」
- 會自動關閉 resource
- ❌ 不需要 try-with-resources


## readString（推薦）

```java
String text = Files.readString(path);
```


### 特色：

- 直接讀成 String
- 只適用「文字檔」
- ✔ 比 readAllBytes 更適合文字處理

Java 11+

##  readAllLines（舊但常用）

```java
List<String> lines = Files.readAllLines(path);
```

### 特色：

- 一行一個 String
- 適合逐行處理


# 3. Files.lines（ Stream 版本）

```java
Files.lines(path)
```

### 特色：

- 回傳 `Stream<String>`
- lazy execution（延遲執行）
- 必須 close（try-with-resources）


## 很重要

```java
try (Stream<String> lines = Files.lines(path)) {}
```

> 不 close 會資源洩漏

#  5. 三種檔案分析方式

---

## 方式 1：readAllLines + loop

```java
Files.readAllLines(path).forEach(...)
```

> 傳統寫法


## 方式 2：Stream pipeline（推薦）

```
Files.lines(path)  .map(...)  .filter(...)  .collect(...)
```

> 最現代寫法


## 方式 3：Scanner + regex

> 另一種方式


#  6. Stream 做資料分（重點🔥）


## 去重複 + 排序

```java
.distinct().sorted()
```

---

## skip header

```
.skip(1)
```

---

## trim 清資料

```java
.map(String::trim)
```

---

## group by + count

```java
Collectors.groupingBy(dept,    Collectors.counting())
```

變成：

```
Finance → 5HR → 4IT → 6
```

# 7. 三大 Files 方法總結

|方法|回傳|用途|
|---|---|---|
|readAllBytes|byte[]|最底層|
|readString|String|最簡單|
|readAllLines|List<String>|逐行|
|lines|Stream<String>|資料分析|

# 8. 重要限制

這些方法：

> ❗ 都會把整個檔案讀進記憶體

### 限制：

- 約 2GB 以上容易 OOM（OutOfMemoryError）


#  9. 適用場景

## ✔ 適合：

- log
- CSV
- config
- 小中型檔案

大檔案不適合用 Files 一次讀入

問題：

- 記憶體壓力大
- 約 2GB 以上可能 OOM（OutOfMemoryError）
  
## ❌ 不適合：


- 巨型檔案
- streaming data

上面那些建議用：

- BufferedReader
- Channel（下一章）

| 方法           | 回傳     | 是否整檔載入  | 用途       |
| ------------ | ------ | ------- | -------- |
| readAllBytes | byte[] | 是       | binary   |
| readString   | String | 是       | text（推薦） |
| readAllLines | List   | 是       | 小檔逐行     |
| lines        | Stream | 否（lazy） | 大檔分析     |
