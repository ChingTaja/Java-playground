## 1. `Files.write / writeString`

```java
Files.write(path, data);
Files.writeString(path, header);
```
特性：
- 一次性寫入
- 每次呼叫 = 開檔 → 寫入 → 關檔
- 預設會 **truncate（覆蓋）**

## 2. `BufferedWriter`

```java
BufferedWriter writer = Files.newBufferedWriter(Path.of("take2.csv"));
```
重點：

- 手動控制寫入流程
- 可以 `write()` + `newLine()`
- 適合大量資料寫入
- 需要 try-with-resources


## 3. `FileWriter`

```java
FileWriter writer = new FileWriter("take3.csv");
```

特性：

- 比 BufferedWriter 更低階
- **沒有 newLine()**
- 通常會再包 BufferedWriter


## 4. `PrintWriter`（最彈性的版本）
java
```
PrintWriter writer = new PrintWriter("take4.csv");
```

特性：

- 可以用：
    - `println()`
    - `printf()`
    - `format()`
- 可直接寫物件風格輸出
- 常用於報表 / fixed width / debug output


# 二、程式碼「前後差異」

## ① 原本版本（Files.write）

```java
Files.write(path, student.getEngagementRecords(), APPEND);
```

### 問題：

- 每次 loop 都開關檔
- 效率差
- 預設會覆蓋內容（如果沒 APPEND）


## ② 改良版本（一次寫入 List）

```java
List<String> data = new ArrayList<>();
data.add(header);
data.addAll(records);
Files.write(path, data);
```

### 改進：

- 只寫一次檔案
- 效率提升很多
- 結構更乾淨

---

## ③ BufferedWriter 版本（逐行控制）

```java
writer.write(header);
writer.newLine();
for (...) {
    writer.write(record);
    writer.newLine();
    }
```

### 差異：

- 你自己控制「每一行」
- 可即時 flush（進階）
- 適合 streaming output

---

## ④ FileWriter / PrintWriter 差異

### FileWriter

```
writer.write(...)
```

❌ 沒 newLine 
❌ 要自己加 `\n`

---

### PrintWriter

```
writer.println(...)
```

 自動換行  
 支援格式化  
 


> Files.write = 一次性寫檔工具（簡單但不彈性）
> Writer 系列 = 逐行控制 + buffer + 可格式化輸出（進階寫檔方式）