 優點

coding to interface 可以讓程式更好擴充、比較好重構

為什麼？

因為寫的是：
```java
List list = new ArrayList();

// 未來可以換：
```
ArrayList → LinkedList （換成不同實作）


👉 不用改使用端程式碼

但問題來了
❌ interface 改一個方法 = 全部爆掉

假設你有：
```java
interface FlightEnabled {
    void fly();
}
😱 如果你新增一個方法：
interface FlightEnabled {
    void fly();
    void land(); // 新增
}
```
 結果：💥

👉 所有實作這個 interface 的 50 個 class：

class Bird implements FlightEnabled // ❌ 會報錯

因為：

少 implement land()
 這就是問題

👉 interface 一改 = 所有實作 class 可能全部壞掉

原因:

interface 是「合約」

👉 你改合約 = 所有人都要重新簽

- 缺點總結
1. 不向後相容	 ,改 interface 會讓舊 code 壞掉
2. 牽一髮動全身	, 很多 class 要一起改


# Java 在 JDK 8 做了改進

 之後會加入：

default method
static method

讓 interface 可以「加新功能但不炸掉舊 code」



#  interface 很好用，但一旦改動，會影響所有實作它的 class
1. 優點：
擴充性好
解耦
可替換實作

2. 缺點：
不容易修改（breaking change）