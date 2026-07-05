# 從舊 IO（File）正式帶到現代 NIO.2（Path + Files）

##  File（舊）

特點：

- 回傳 boolean（true / false）
- 方法分散
- 行為不一致


```java
File file = new File(filename);  
  
boolean exists = file.exists();  
  
if (exists) {  
file.delete(); // 回傳 boolean  
}  
  
if (!file.exists()) {  
file.createNewFile(); // 回傳 boolean + IOException  
}  
  
file.canWrite();
```

風格：LBYL（先檢查）

---

##  NIO.2（新）

特點：

- 統一用 Files
- Path 代表位置
- 失敗直接 throw exception

例子：

```java
Path path = Path.of(filename);
Files.exists(path);
  
Files.delete(path); // 失敗直接 exception
  
Files.createFile(path);
  
Files.isWritable(path);
```

風格：EAFP（出錯再處理）

---

# 設計變化

## ❌ 舊 File：

- 成功/失敗 → boolean

## ✅ 新 Files：

- 成功 → nothing
- 失敗 → IOException

結論：

> Java 從「回傳結果」改成「例外驅動」

---

# writeString / readAllLines（現代 API）

##  寫檔

```java
Files.writeString(path, """
line1
line2
line3
""");
```

---

## 讀檔

```java
Files.readAllLines(path)
.forEach(System.out::println);
```

---

# 5為什麼 NIO.2 比 File 好？


## 功能更強

- async I/O
- file locking（甚至局部鎖）
- metadata access
- symbolic link 支援
- file watch（監控變化）

---

## 更高效

- non-blocking（非阻塞）
- buffer-based I/O
- FileChannel（直接操作記憶體）

---

## 更現代架構 分層清楚

- Path（純位置）
- Files（操作工具）


---

# 什麼時候用 File？

只有一個原因：

> 要支援 Java 7 以前的 legacy code



分層式設計（layered design）本質上是在解決一件事：

> 把「不同責任的事情拆開」，讓系統更穩、更好改、更不容易壞