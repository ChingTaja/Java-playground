package Hashing.CollectionInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapMain {

    public static void main(String[] args) {

        List<Contact> phones = ContactData.getData("phone");
        List<Contact> emails = ContactData.getData("email");

        List<Contact> fullList = new ArrayList<>(phones);
        fullList.addAll(emails);
        fullList.forEach(System.out::println);
        System.out.println("-----------------------------");

        Map<String, Contact> contacts = new HashMap<>();

        for (Contact contact : fullList) {
            // Map 不能用 add / addAll (Collection 才有) -> Map 用的是：put(key, value)
            contacts.put(contact.getName(), contact);
        }
        contacts.forEach((k, v) -> System.out.println("key=" + k + ", value= " + v));

        System.out.println("-----------------------------");
        System.out.println(contacts.get("Charlie Brown"));

        System.out.println(contacts.get("Chuck Brown"));

        Contact defaultContact = new Contact("Chuck Brown");
        System.out.println(contacts.getOrDefault("Chuck Brown", defaultContact));

        System.out.println("-----------------------------");
        contacts.clear();
        for (Contact contact : fullList) {
            Contact duplicate = contacts.put(contact.getName(), contact);
            if (duplicate != null) {
                // System.out.println("duplicate = " + duplicate);
                // System.out.println("current = " + contact);
                contacts.put(contact.getName(), contact.mergeContactData(duplicate));
            }
        }
        contacts.forEach((k, v) -> System.out.println("key=" + k + ", value= " + v));

        System.out.println("-----------------------------");
        contacts.clear();

        for (Contact contact : fullList) {
            // 僅在鍵不存在時才執行插入，這對於「保留原始版本」的場景非常有用
            contacts.putIfAbsent(contact.getName(), contact);
        }
        contacts.forEach((k, v) -> System.out.println("key=" + k + ", value= " + v));

        System.out.println("-----------------------------");
        contacts.clear();

        for (Contact contact : fullList) {
            Contact duplicate = contacts.putIfAbsent(contact.getName(), contact);
            if (duplicate != null) {
                contacts.put(contact.getName(), contact.mergeContactData(duplicate));
            }
        }
        contacts.forEach((k, v) -> System.out.println("key=" + k + ", value= " + v));

        System.out.println("-----------------------------");
        contacts.clear();
        fullList.forEach(contact -> contacts.merge(contact.getName(), contact,
                Contact::mergeContactData));
        contacts.forEach((k, v) -> System.out.println("key=" + k + ", value= " + v));

        System.out.println("-----------------------------");

        // =====================
        // 1. compute 與 computeIfAbsent 的行為差異
        // =====================
        for (String contactName : new String[] { "Daisy Duck", "Daffy Duck", "Scrooge McDuck" }) {
            // 【粗暴更新】compute：不論 Key 存在與否，一律執行 Lambda。
            // 缺點：若 Daffy Duck 原本已有資料，他的舊資料（Email/電話）會直接被這個 new 出來的空物件覆蓋
            contacts.compute(contactName, (k, v) -> new Contact(k));

            // 【安全防護】computeIfAbsent：只有在 Key「不存在（Absent）」時，才執行 Lambda 建立新物件。
            // 優點：Daffy Duck 因為原本就存在，所以會維持原樣，不會被覆蓋，非常適合用於「保留原始版本」。
            contacts.computeIfAbsent(contactName, k -> new Contact(k));
        }
        contacts.forEach((k, v) -> System.out.println("key=" + k + ", value= " + v));

        System.out.println("-----------------------------");

        // =====================
        // 2. computeIfPresent (只有存在時才處理)
        // =====================
        for (String contactName : new String[] { "Daisy Duck", "Daffy Duck", "Scrooge McDuck" }) {
            // computeIfPresent：只有當 Key「存在」時，才會執行後續的 Lambda。
            // Lambda (k, v) 的 v 代表目前地圖裡的舊物件，修改完後必須 return 該物件以更新 Map。
            contacts.computeIfPresent(contactName, (k, v) -> {
                v.addEmail("Fun Place");
                return v; // 記得 return 修改後的數值 (Value)
            });
        }
        contacts.forEach((k, v) -> System.out.println("key=" + k + ", value= " + v));

        System.out.println("-----------------------------");

        // ======================
        // 3. replaceAll (整份批次更新)
        // =====================
        // replaceAll：類似 List.replaceAll()，遍歷 Map 中的每一筆資料並進行修改。
        // 注意：它會對「所有」元素做處理
        // 雖然在只想改特定幾筆時效能不高，但適合用來做全局的資料清洗（格式化）
        contacts.replaceAll((k, v) -> {
            String newEmail = k.replaceAll(" ", "") + "@funplace.com";
            v.replaceEmailIfExists("DDuck@funplace.com", newEmail);
            return v; // 必須返回新的或修改後的 Value 物件
        });
        contacts.forEach((k, v) -> System.out.println("key=" + k + ", value= " + v));

        System.out.println("-----------------------------");

        // ========================
        // 4. replace(key, newValue) 基本覆蓋與返回值
        // ========================
        Contact daisy = new Contact("Daisy Jane Duck", "daisyj@duck.com");

        // replace(key, value)：將 "Daisy Duck" 的值換成新的 daisy 物件。
        // 返回值：它會回傳「被換掉的舊 Value」（即 replacedContact），方便你保留歷史記錄。
        // 注意：此時 Map 中的 Key 雖然維持 "Daisy Duck"，但內部的 Contact 姓名物件已經變成 "Daisy Jane Duck"。
        Contact replacedContact = contacts.replace("Daisy Duck", daisy);
        System.out.println("daisy = " + daisy);
        System.out.println("replacedContact = " + replacedContact); // 印出前任舊資料
        contacts.forEach((k, v) -> System.out.println("key=" + k + ", value= " + v));

        System.out.println("-----------------------------");

        // =====================
        // 5. replace(key, expectedOldValue, newValue) 安全比對覆蓋
        // ======================
        Contact updatedDaisy = replacedContact.mergeContactData(daisy);
        System.out.println("updatedDaisy = " + updatedDaisy);

        // 三個參數的 replace：具備Optimistic Locking） 概念
        // 規則：只有當【Key 是 "Daisy Duck"】且【目前 Map 裡的 Value 必須「完全等於」replacedContact】時，才會成功替換為
        // updatedDaisy。
        // 失敗原因：因為前面已經把值換成 "Daisy Jane Duck" 了，與預期的 replacedContact(Daisy Duck)
        // 名字不符（equals 失敗），所以這步 success 會是 false。
        boolean success = contacts.replace("Daisy Duck", replacedContact, updatedDaisy);

        // 💡 修正補救）：若將中間參數改成 daisy（也就是剛才塞進去的 Daisy Jane），比對成功就會回傳 true。
        // boolean success = contacts.replace("Daisy Duck", daisy, updatedDaisy);

        if (success) {
            System.out.println("Successfully replaced element");
        } else {
            System.out
                    .println("Did not match on both key: %s and value: %s %n".formatted("Daisy Duck", replacedContact));
        }
        contacts.forEach((k, v) -> System.out.println("key=" + k + ", value= " + v));

        System.out.println("-----------------------------");

        // ====================
        // 6. remove(key, expectedValue) 安全比對刪除
        // ========================
        // 兩個參數的 remove：同樣具備安全驗證機制。
        // 規則：只有當 Key 存在，且 Map 中的物件「等於（equals）」你傳入的第二個參數 daisy 時，才執行移除。
        // 實驗結果：此時地圖中是更新過的資料（擁有2個Email的完整版），與只有單個Email的 daisy 不相等，因此比對失敗，拒絕移除。
        success = contacts.remove("Daisy Duck", daisy);
        if (success) {
            System.out.println("Successfully removed element");
        } else {
            System.out.println("Did not match on both key: %s and value: %s %n".formatted("Daisy Duck", daisy));
        }
        contacts.forEach((k, v) -> System.out.println("key=" + k + ", value= " + v));
    }
}
