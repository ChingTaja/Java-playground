# static variable

定義： 使用 static 關鍵字宣告的變數
特性： 所有該 class 的 instance 共享同一個靜態變數。
=>
如果某個 instance 改變了這個變數
其他 instance 也會看到改變的結果

用類別名稱而不是 instance reference 來存取靜態變數，這樣更清楚它是屬於類別的，而不是屬於實例的

```java
class Dod {
    static String genus = "Canis";

    void printData() {
        Dog d = new Dog();
        System.out.println(d.genus); // Confusing !! don't do this thing
        System.out.println(Dog.genus); // clearer~
    }
}
```

###
An instance isn't required to exist to access the value of a static variable.

```java
class Dog {
    static String genis = "Canis"; // static variable

    public Dog(String name) {
        this.name = name; // instance variables
    }

    public void printName() {
        System.out.println("name = " + name);
    }
}

class Main {
    public static void main(String[] args) {
        // 沒有 new Dog 物件也可以存取靜態變數
        System.out.println(Dog.genus);

        Dog rex = new Dog("rex");
        Dog fluffy = new Dog("fluffy");
        rex.printName(); // rex
        fluffy.printName(); // fluffy
    }
}
```

### 用途
1. 儲存計數器（counter）
2. 生成唯一 ID
3. 儲存不會改變的常數，例如 PI
4. 創建與控制共享資源（例如 log 檔、資料庫)

# instance variables
instance variables belongs to **a specific instance** of a class