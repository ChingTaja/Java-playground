# 把「寫檔方式」從 Files.write 升級成三種 Writer 類別，並比較差異 + 加上 Buffered / flush 概念


# 一、 新增/改的程式碼有哪些？

## 1. 新增 FileWriter 版本（take3.csv）

### ✔ 新增 try block

```java
try (FileWriter writer = new FileWriter("take3.csv")) {
```

### 改動點

- ❌ 不再用 Files.write
- ❌ 沒有 newLine()
- ✔ 改用 writer.write()

但問題：

- 沒有自動換行
- 需要自己加 `\n` 或 `System.lineSeparator()`


## 2. 新增 PrintWriter 版本（take4.csv）

### 新增 try block

```java
try (PrintWriter writer = new PrintWriter("take4.csv")) {
```

### 改動點

#### (1) write → println

```java
writer.println(record);
```

好處：

- 自動換行
- 比 FileWriter 更方便


#### (2) 新增 printf / format（固定格式輸出）

```
writer.printf(...)
writer.format(...)
```

 用來做：

- fixed-width file（欄位對齊）

## 3. 新增 BufferedWriter 行為（take2.csv 改進概念）

### 新增操作：

#### (1) 手動 flush

```java
writer.flush();
```

#### (2) 控制寫入節奏

```java
if (count % 10 == 0) {    writer.flush();}
```

#### (3) 模擬延遲

```java
Thread.sleep(2000);
```

# 二、前 vs 後（重點差異）

## ① Files.write（前）

```java
Files.write(path, data);
```

### 特色：

- 一次寫完
- 自動 open/close
- 簡單但不可控

## ② FileWriter（中階）

```java
FileWriter writer = new FileWriter(path);
writer.write(...)
```

### 特點：

- 需要自己處理換行
- 有 buffer 但不保證大小
- 較底層

## ③ PrintWriter（進階）

```java
PrintWriter writer = new PrintWriter(path);
writer.println(...)
```

### 特色：

- 最方便（println / printf）
- 可以寫 object
- 支援格式化輸出
- 內部包 BufferedWriter

## ④ BufferedWriter（底層控制）

```java
BufferedWriter writer = Files.newBufferedWriter(path);
```

### 特色：

- 最有效率（大資料）
- 可控制 flush
- 需要自己寫 newLine()

# 三、重要觀念

## 1. 三種寫法層級

```
Files.write → 最簡單（高階）
FileWriter → 中階（手動控制）
PrintWriter → 方便 + 格式化
BufferedWriter → 最底層 + 最可控
```


## 2. buffer 概念（超重要）

### ❗寫檔不是「即時寫入」

- 先進 buffer
- 滿了才寫 disk
- close 才強制 flush


## 3. flush 的意義

> 強制寫入檔案


## 四、總結

比較「4種寫檔工具」：

- Files.write（最簡單）
- FileWriter（基礎寫入）
- PrintWriter（最好用）
- BufferedWriter（最底層、最有效率）
