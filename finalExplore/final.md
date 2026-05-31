## final 的概念說明

重要的是要理解，當使用 `final` 時，並不代表這個變數本身是「不可變（immutable）」的

它的真正意思是：

1. 在初始化之後，你**不能再將新的 instance、變數或 expression 重新指派（reassign）給它**
2. `final` 的核心不是「資料不能改」，而是「reference 不能重新指向別的東西」


- 一個 `final` 方法意味著它不能被子類別覆寫
- 一個 `final` 欄位意味著該物件的欄位在初始化之後，就無法被重新賦值或給予不同的數
- 一個 `final static` 欄位則是一個類別欄位，它在類別的初始化程序完成後就無法被重新賦值或變更數值
	在介面上宣告的欄位永遠都是 `public`、`static` 且 `final` 的
- 一個 `final` 類別無法被繼承，這意味著沒有任何類別可以在 `extends` 子句中使用它
- 在程式碼區塊中的 `final` 變數意味著一旦它被賦值，該區塊中剩餘的程式碼就無法再改變它
- 一個 `final` 方法參數則意味著你無法在該方法的方法程式碼區塊中為該參數賦予不同的數值

只有當你想限制子類別可以覆寫或隱藏哪些內容時，使用 `final` 才具有意義

- 在實例方法（Instance Method）上使用 `final` 意味著子類別無法覆寫他
- 在類別（靜態）方法（Class/Static Method）上使用 `final` 則意味著子類別無法隱藏（Hide）它
  
| **特性**        | **實例方法的「覆寫（Override）」** | **靜態方法的「隱藏（Method Hiding）」** |
| ------------- | ----------------------- | ---------------------------- |
| **方法類型**      | 一般方法（沒有 `static`）       | **靜態方法（有 `static`）**         |
| **決定呼叫誰的時間點** | **執行期（Runtime）**：看記憶體實體 | **編譯期（Compile-time）**：看變數型別  |
| **機制類型**      | 動態綁定（Dynamic Binding）   | 靜態綁定（Static Binding）         |

## local variable（區塊內變數）

如果在 code block 中對 local variable 使用 `final`：

- 你只能「完整初始化一次」或「只賦值一次」
- 之後任何再次 assignment 都會導致 compiler error

例如：

✔ 合法（只賦值一次）

```java
final int x = 10;
```

❌ 非法（重複賦值）

```java
final int x = 10;
x = 20; // compiler error
```

---

## method parameters（方法參數）

如果 `final` 用在 method parameters 上：

- 代表你不能在 method 內部重新 assign 這些參數

原因是： method arguments 在 method 被呼叫時就已經「隱式初始化（implicitly initialized）」

例如：

```java
void print(final int value) { 
	 value = 10; // compiler error
	 }
```


### CharSequence

**`CharSequence`** 就是 Java 世界裡所有「字串相關類別」的**共同祖先（介面，Interface**

不論是我們天天在用的、不可變的 `String`，還是用來動態拼接字串的 `StringBuilder` 和 `StringBuffer`
它們在底層都實作（Implement）了 `CharSequence` 這個介面

## `CharSequence` 介面定義了什麼？

既然它是個介面（Interface），它就定義了所有字串家族都必須遵守的「基本四大功能」：

1. **`length()`**：獲取這個字串長度。
    
2. **`charAt(int index)`**：抓取某個位置的字元（`char`）。
    
3. **`subSequence(int start, int end)`**：切出一小段字串。
    
4. **`toString()`**：轉換成標準的 `String` 物件。
    

不論子類別底層是用什麼陣列儲存資料，只要實作了這四個方法，它就是一個合格的 `CharSequence`


