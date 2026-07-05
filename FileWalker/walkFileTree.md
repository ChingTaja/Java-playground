## 如何遞迴走訪整個資料夾樹

#  walkFileTree 是什麼

```java
Files.walkFileTree(startingPath, visitor);
```

> 從 startingPath 開始  
> 遞迴拜訪所有檔案與資料夾
> visitor 負責決定每個節點要做什麼

---

# walk 是「深度優先（Depth First）」

 Depth First（深度優先）意思：
> 先一路走到底 
> 再回來處理兄弟節點

## 圖解

```
root
 ├── out
 │    └── a.txt
 └── src
      └── Main.java
```

### Depth First 走法：

```
root → out
		→ a.txt
	 → src
	    → Main.java
```

# Breadth First （廣度優先）

會變成：

```
root 
	→ out 
	→ src 
	→ a.txt 
	→ Main.java
```


但是：walk / walkFileTree 都不是這種

---

#  FileVisitor 是什麼

這是：

> 「當走到某個點時要做什麼」的介面

進資料夾時做什麼
離開資料夾時做什麼
看到檔案時做什麼
失敗時做什麼


---

#  四個方法

| 方法                 | 時機     |
| ------------------ | ------ |
| preVisitDirectory  | 進資料夾前  |
| postVisitDirectory | 離開資料夾後 |
| visitFile          | 看到檔案時  |
| visitFileFailed    | 檔案失敗   |

---

# SimpleFileVisitor 是什麼

```java
extends SimpleFileVisitor<Path>
```

意思：

> 幫你做好預設實作


否則：你要自己實作 FileVisitor 全部方法 , 很麻煩~

---

# 為何 override visitFile

```java
@Overridepublic FileVisitResult visitFile(...)
```

因為：

> 你要定義「看到檔案時做什麼」

---

例如：

```java
System.out.println(file.getFileName());
```

---

# FileVisitResult 是什麼

每個方法都要回傳： FileVisitResult

## 常見值

|值|意思|
|---|---|
|CONTINUE|繼續|
|TERMINATE|停止|
|SKIP_SUBTREE|跳過子樹|
|SKIP_SIBLINGS|跳過兄弟節點|

---

# preVisitDirectory / postVisitDirectory 的用途


```
level++
level--
```


 目的：追蹤目前深度

### 進資料夾

```
level++
```

---

### 離開資料夾

```
level--
```

---

# 為何這樣做

為了：縮排顯示樹狀結構

---

# 總結
## walk

只是簡單遞迴


## walkFileTree + FileVisitor

才是 真正可控制的檔案樹走訪

他們可以：

- 統計檔案數
- 搜尋大檔案
- 刪除資料夾
- 建立樹狀結構
- 計算容量
- 權限檢查
- 備份工具
