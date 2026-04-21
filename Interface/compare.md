# Abstract Class vs Interface

一、共同點
❌ 都不能被實例化（new）

✔ 都可以有：
抽象方法（沒有 {}）
具體方法（有 {}）

✔ 都是「抽象型別（reference type）」

二、Abstract Class

### 特性
✔ 可以有：
instance fields（例如 name, age）

✔ 方法可以用所有存取修飾詞：
public / protected / private / default

✔ 可以有 constructor（給子類用）

✔ 只能 extends 一個 class

✔ 可以 implement 多個 interface

### 行為
子類別通常要實作所有 abstract method
如果沒實作 👉 子類也必須是 abstract

### 適用

有共用邏輯 + 有狀態的基底類別

三、Interface

### 特性
❌ 沒有 instance fields

✔ 只有： public static final 欄位（常數）

✔ 方法預設：
沒 body → public abstract

✔ 可以：
default method
static method
private method（JDK 9）

定義「能做什麼」(what)，不是「怎麼做」(how)

### 關係
✔ interface 可以 extend 多個 interface
✔ class 可以 implement 多個 interface

### 適用情境

不相關的類別共享行為

decoupling


### Abstract Class vs Interface（
| 項目         | Abstract Class  | Interface         |
| ----------- | --------------------- | ------------------ |
| 是否可實例化   | ❌ 不可              | ❌ 不可              |
| constructor | ✔ 可以有（給子類用）  | ❌ 不可以          |
| 繼承方式        | `extends`  | `implements`              |
| 是否屬於 class  | ✔ 是 class    | ❌ 不是 class 
| 繼承限制        | 單一繼承（只能 extends 一個）| 可多個實作（implements 多個）               |
| 是否繼承 Object | ✔ 會自動繼承 `Object`   | ❌ 不會         |
| 方法類型        | 抽象 + 具體方法      | 抽象 + 部分具體方法  |
| 具體方法限制      | 無限制     | 只能是：default / static / private |
| 抽象方法修飾詞   | 可用 public / protected | 預設就是 public abstract |
| 方法預設行為      | 不會自動加修飾詞   | 無 body → 自動 `public abstract` |
| default 方法  | ❌ 不支援     | ✔ 支援（JDK 8）            |
| private 方法  | ✔ 支援     | ✔ 支援（JDK 9）               |
| static 方法   | ✔ 支援      | ✔ 支援（JDK 8）         |
| 欄位（fields）| ✔ 可有 instance fields | ❌ 不可          |
| 欄位限制    | 無限制       | 只能是 `public static final`     |
| 設計目的      | 共用邏輯 + 狀態       | 定義行為（contract）           |
| 是否參與繼承體系    | ✔ 是        | ❌ 否（只是規範）             |




# 五、什麼時候用哪個

∆ 用 Abstract Class 當：
- 有「共用資料（state）」
- 類別彼此高度相關
- 想提供預設實作
- 需要控制存取（private/protected）

∆ 用 Interface 當：
- 類別之間不相關
- 只想定義「行為」
- 想要彈性 + 可替換
- 想做解耦 / 架構設計