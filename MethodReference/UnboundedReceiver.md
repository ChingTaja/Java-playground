method reference 的一種

Method Reference 的本質不是「語法糖」，而是：

它一定對應某個 Functional Interface 的方法簽名

左邊怎麼寫（::）
完全取決於右邊 functional method 怎麼定義（參數數量 / 型別）

### Type Reference（類型參考） 
    
指的是類別名稱、介面名稱、列舉名稱或 record 名稱

static 方法通常是透過 Type Reference 呼叫，但也可以透過物件實例呼叫

範例（static method）
```java
// Integer 這個部分 是 Type Reference（型別參考）
Integer::sum
```

等同於 lambda：
```java
(a, b) -> Integer.sum(a, b)
```
解釋:
Integer 是「類別」
sum 是 static method
不需要物件

> 這些只是「型別名字」，還不是物件

### Instance Method

一定要「透過物件」來呼叫的方法
```java
"abc".length()
System.out.println("hi")
```
重點：instance method = 一定要有「誰」來呼叫它

👉 「誰」 ==> instance method 有兩種「物件來源方式」
1. Bound method reference
2. Unbound method reference

### Bounded Receiver (物件已經「先決定好了」)

```java
System.out::println
```
等同於：
```java
s -> System.out.println(s)
```

解釋（重點）
```
System.out
```
其實是：
```
PrintStream out = System.out;
```

所以：
out 是已存在的物件（instance）
println 是 instance method

👉 已經「綁定物件」，所以叫 bounded
> 「我已經告訴你要用 System.out 這個人來做事」


### Unbounded Receiver( 物件「不是先給」，而是執行時才決定)

    
```java
// 其實不是「呼叫 class method」
// 把第一個參數當成 instance
// 必須搭配： BiFunction<String, String, String>

String::concat
```

等同於：
```java
(a, b) -> a.concat(b)
```
    
拆解

假設 functional interface：
```java
BiFunction<String, String, String>
```

執行時會變成：
```java
(a, b) -> a.concat(b)
```

| 角色          | 意義          |
| ----------- | ----------- |
| a           |==第一個參數（當作物件）== |
| b           | 第二個參數       |
| a.concat(b) | 真正執行的方法     |



# 為什麼會混淆？

```java
String::concat
Integer::sum
```

| 情況             | 真正意思                       |
| -------------- | -------------------------- |
| Integer::sum   | static method              |
| String::concat | instance method（unbounded） |

## 記法
    
Bounded : 物件已經選好了
Unbounded: 物件 = 第一個參數
Static: 沒有物件（class 直接用）