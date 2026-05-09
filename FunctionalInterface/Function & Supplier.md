# Function 類別包含四種常見介面
四種常見介面
- Function：單一輸入、單一輸出
- BiFunction：兩個輸入、單一輸出
- UnaryOperator：單一輸入、輸出型別相同
- BinaryOperator：兩個輸入、輸出型別相同

1️⃣ Function 類（轉換型）

👉 特徵：輸入 → 轉換 → 輸出（可不同型別）

✔ Function<T, R>
方法：R apply(T t)
參數：1 個
功能：把 T 轉成 R
例子：
String → Integer（length）
Integer → String

✔ BiFunction<T, U, R>
方法：R apply(T t, U u)
參數：2 個
功能：兩個輸入 → 一個輸出（可不同型別）
例子：
(String, String) → String concat
(Integer, Integer) → Integer sum

2️⃣ Operator 類（運算型） 把「通用功能」縮成「更限定、更方便用的版本」

👉 特徵：輸入 = 輸出（同型別）, 類型因型別一致，只需一個泛型參數

✔ UnaryOperator<T>
方法：T apply(T t)
參數：1 個
功能：同型別操作（修改 / 轉換 / 更新）, 單一參數 + 回傳同型別結果
例子：
String → String（toUpperCase）
Integer → Integer（+1）

✔ BinaryOperator<T>
方法：T apply(T t1, T t2)
參數：2 個
功能：兩個同型別做運算 , 兩個參數 + 回傳同型別結果
例子：
Integer + Integer → Integer
String + String → String

3️⃣ Primitive specialization（基本型別優化）

👉 特徵：避免 boxing（效能優化版本）

✔ IntFunction<R>
方法：R apply(int value)
參數：1 個 int
功能：int → R（通常是 index 或計算結果）
例子：
int → String
int → Double
用於 array / stream mapping


Function：一般轉換（T → R）
BiFunction：雙輸入轉換（T, U → R）
UnaryOperator：同型轉換（T → T）
BinaryOperator：同型運算（T, T → T）
IntFunction：primitive 優化版（int → R）


# Supplier 介面

不接收任何參數**，但會回傳某種類型 T 的實例(an instance of some type)

想成類似「工廠方法（factory method）」的概念

但這不一定代表每次都會回傳一個全新或不同的結果

Supplier：無輸入、有回傳（常用於「產生資料」）

✔ Supplier 是什麼
```java
Supplier<Integer> supplier = () -> 3;
```
特點：

1. 沒有輸入參數
2. 有回傳值
3. 常用於「產生資料」

✔ 應用：隨機 index :  每次呼叫都會回傳 0 ~ 5 的隨機數
```java
() -> new Random().nextInt(0, names.length)
```

Supplier =「不需要輸入，但負責產生值」