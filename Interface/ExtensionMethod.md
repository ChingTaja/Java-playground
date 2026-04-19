現在假設有一個新需求：

所有 interface FlightEnabled 的物件，都需要一個新方法

=> 
我把這個方法「加到 interface」裡（抽象方法）
結果：所有實作這個 interface 的 class 全部壞掉！

JDK 8 之前的解法只有強迫全部改 或 開一個新 interface

後來引入 extension method（擴展方法）=> 也可以說是 default method

```java
default FlightStages transition(FlightStages stage) {
    return stage;
}
```
🎈 特點：
有方法內容（不是 abstract）
可以寫邏輯
可以被 override

🎈 優點：舊的 class 不用改！


# default method implementation

在某些情況下，interface 裡的 default method
可以像 class method 一樣，提供「所有 class 都適用的共用行為」
當你 override interface 的 default method，有三種選擇：

### 1. 不 override

直接用 interface 的 default method

### 2. 完全 override

自己寫邏輯，完全取代 default

### 3. 部分 override（最進階🔥）

自己寫邏輯 + 呼叫 default method

```java
FlightEnabled.super.transition(stage);
```

