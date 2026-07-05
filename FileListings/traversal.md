# 如何使用 `Files` 類別「遍歷資料夾（directory traversal）」

方法有

| 方法                   | 功能          |
| -------------------- | ----------- |
| `list`               | 列出單層資料夾     |
| `walk`               | 遞迴走訪資料夾     |
| `find`               | 遞迴搜尋符合條件的檔案 |
| `newDirectoryStream` | 更輕量的資料夾遍歷   |



## `Files.list(path)`

```java
Files.list(path)
```

回傳：

```
Stream<Path>
```

## 功能

只列出：

- 當前資料夾
- 不會進入子資料夾

## 類似：

```
lsdir
```

---

# 為什麼一定要 try-with-resources？

```java
try (Stream<Path> paths = Files.list(path)) {}
```


因為：
```java
Stream<Path>
```

背後其實：

- 開啟 directory resource
- 保持 file handle


##  Stream 是 lazy 的


還沒 terminal operation 前，directory 一直保持開啟


#### 所以一定要：

```
try-with-resources
```

讓 Java 自動 close


# `listDir()` 方法

建立：

```
private static String listDir(Path path)
```

目的是：自訂漂亮的 directory listing

---

## 方法

### 判斷是不是資料夾

```java
Files.isDirectory(path)
```

---

### 取得修改時間

```java
Files.getLastModifiedTime(path)
```

---

### 取得檔案大小

```java
Files.size(path)
```

---

#  `Files.walk(path, depth)`

遞迴走訪資料夾

```java
Files.walk(path, 2)
```


---

## depth 意思

```
2
```

代表：

> 最多走兩層

---

## 和 list 差異

|方法|遞迴|
|---|---|
|list|❌|
|walk|✅|

---

# `filter`

只保留普通檔案



排除：

- directory
- symbolic link

```java
.filter(Files::isRegularFile)
```


---

# `Files.find`

```java
Files.find(path, depth, predicate)
```

 比 walk + filter 更高效

因為：

> 搜尋條件直接整合到底層 traversal


# `BiPredicate`

```java
(p, attr) -> ...
```

---

## 兩個參數

|參數|意思|
|---|---|
|p|Path|
|attr|BasicFileAttributes|

---

## attr 很重要

可以直接拿：

```
attr.isRegularFile()
attr.size()
```

不用再呼叫 Files

---

# 搜尋大檔案

```
attr.size() > 300
```

---

## 搭配：

```
Integer.MAX_VALUE
```

代表：

> 搜尋所有深度

---

# `DirectoryStream`

更輕量的 directory traversal

```
Files.newDirectoryStream(path)
```

---

## 和 Stream 差異

### DirectoryStream

- iterable
- memory 較省
- 適合大資料夾

---

### Files.list

- stream pipeline 很方便
- functional style

---

#  glob pattern

glob = 簡化版的 pattern 匹配語法

簡單檔名匹配

```
"*.xml"
```

---


## 類似：

```
*.txt*.java
```

---

#  `resolve`


在目前路徑「往下一層走」


例子
```java
Path p = Path.of("project");
p.resolve(".idea");
```
結果：
```
project/.idea
```


---

## 類似：

```
current + "/.idea"
```

---

# lambda filter 版本

```
p -> p.getFileName().toString().endsWith(".xml")
```

---

## 比 glob 更自由

因為你可以：

- 判斷大小
- 判斷日期
- 判斷是不是 file
- 任意邏輯


也就是：

> NIO.2 把檔案系統變成「可 stream 化資料」

這是 Java 現代化 API 很重要的設計

---

# 總結

> `list` 用來列單層資料夾，`walk` 用來遞迴遍歷，`find` 用來高效搜尋，而 `DirectoryStream` 是更輕量的大量目錄遍歷方法