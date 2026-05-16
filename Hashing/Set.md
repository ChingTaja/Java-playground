集合框架中的 Set Operations ，探討如何利用 Set 介面的 Bulk Operations 來處理多個數據集之間的關聯性

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