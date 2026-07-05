# Path（路徑結構） + Files（系統操作）如何一起工作

#  Path 的核心用途：解析「路徑結構」


```java
Path path = Path.of("files/subfolder/testing.txt");
```


### 基本資訊

- `getFileName()` → 檔名
- `getParent()` → 父資料夾
- `toAbsolutePath()` → 絕對路徑
- `getRoot()` → 根目錄（absolute 才有）

---

## 絕對路徑

```
relative path → getRoot() = null
```

 這是判斷「是不是絕對路徑」的方法之一

---

#  Path 是「可以被拆解的 tree」

兩種方式遍歷：

---

## 方法 A：iterator（傳統方式）

```java
for (Path p : path.toAbsolutePath()) { }
```

把路徑當「資料夾層級」

---

## 方法 B：getName(index)（推薦）

```java
path.getNameCount()
path.getName(i)
```

優點：
- 可以隨機存取層級
- 比 iterator 更靈活

---

## 核心理解

> Path = 一條「可拆解的路徑樹」

---

# Files.createDirectory vs createDirectories



---

## ❌ createDirectory

```java
Files.createDirectory(path);
```

限制：
- 父資料夾必須存在
- 只能建立一層
- 否則 → NoSuchFileException

---

## ✅ createDirectories

```java
Files.createDirectories(path);
```

功能：

- 自動建立所有不存在的資料夾
- 整條路徑一起建
- 一次解決整條路徑

> createDirectory = 建單層
> createDirectories = 建整條樹

 
#  writeString + Options（進階寫檔）

```java
Files.writeString(
	path,
	"hello",
	StandardOpenOption.CREATE,
	StandardOpenOption.APPEND
);
```

---

## Options 重點

|Option|意思|
|---|---|
|CREATE|沒檔案就建立|
|APPEND|接在後面，不覆蓋|

---

## 核心概念

> Files.writeString = 「一行完成建立 + 寫入」

---

#  Files.readAttributes（檔案 metadata）

```java
Files.readAttributes(path, "*");
```

---

## 回傳內容（OS 層資訊）

- size
- lastModifiedTime
- creationTime
- lastAccessTime

 這不是檔案內容，而是：

> 「檔案本身的資訊（metadata）」

---

# probeContentType

```java
Files.probeContentType(path);
```

輸出：
```
text/plain
```

---

## 用途

- 判斷檔案類型（MIME type）
- 類似網頁 Content-Type


### Path 負責「描述檔案位置」，Files 負責「對作業系統執行檔案操作」，兩者組合才是 NIO.2 的完整設計