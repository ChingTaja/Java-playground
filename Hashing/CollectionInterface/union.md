# 集合框架中的 Set Operations ，探討如何利用 Set 介面的 Bulk Operations 來處理多個數據集之間的關聯性

### Union

聯集運算將兩個集合的內容合併
在 Java 中
使用 addAll 方法來達成

於 Set 本身不允許重複
因此合併過程中重複的元素（根據 equals 與 hashCode 判定）只會保留一份

```java
// 建立 Set A (Email 聯絡人) 與 Set B (電話聯絡人)
Set<Contact> emailContacts = ContactData.getData("email");
Set<Contact> phoneContacts = ContactData.getData("phone");

// 執行聯集：A ∪ B
Set<Contact> unionAB = new HashSet<>(emailContacts); // 先包含 A 的所有元素
unionAB.addAll(phoneContacts); // 加入 B，自動過濾與 A 重複的項目

// 註解：此處 unionAB 現在包含所有獨特的聯絡人名稱。
// 規則遵守：Java 雖然沒有聯集專屬方法，但 addAll 在 Set 上運作等同於 Union
```

### Intersection

交集運算用於找出兩個集合中共有的元素
透過 retainAll 方法實作
該方法會「保留」參數集合中也存在的元素，其餘刪除

````java
// 執行交集：A ∩ B
Set<Contact> intersectAB = new HashSet<>(emailContacts);
intersectAB.retainAll(phoneContacts); // 僅保留同時存在於 email 與 phone 列表中的人

// 註解：此操作是「對稱的 (Symmetric)」，意即 A ∩ B 等於 B ∩ A
// 重點：當發生碰撞時，Set 會保留「第一個」被加入的物件實例，後續重複者



### Set Difference
用於找出存在於 A 但不存在於 B 的元素
透過 removeAll 方法實作
```java
// 執行差集：A - B (存在於 A 但不在 B)
Set<Contact> aMinusB = new HashSet<>(emailContacts);
aMinusB.removeAll(phoneContacts); // 移除所有也在 B 集合中出現過的元素

// 註解：此操作是「非對稱的 (Asymmetric)」，A - B 的結果（只有 Email 沒電話的人）
// 與 B - A 的結果（只有電話沒 Email 的人）通常不同
````

### Symmetric Difference

指「存在於 A 或 B 中，但不同時存在於兩者」的元素集合

```java
// 方法一：(A - B) ∪ (B - A)
Set<Contact> symmetricDiff = new HashSet<>(aMinusB);
symmetricDiff.addAll(bMinusA);

// 方法二：(A ∪ B) - (A ∩ B)
Set<Contact> symmetricDiff2 = new HashSet<>(unionAB);
symmetricDiff2.removeAll(intersectAB);

// 註解：這兩種方法產生的結果完全相同，皆為不具備重疊資料的聯絡人集合
```

### 雜湊集合不會的變特性

無論 Java 如何更新 HashSet 的底層實作（例如目前使用 HashMap），其對外行為始終一致：

不允許重複

不保證順序

接近常數時間 O(1) 的存取效能（前提是 hashCode 均勻分佈於 Buckets 中）
