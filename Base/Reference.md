我們是透過 reference 存取物件，而不是直接存取物件本身
Reference => 指向物件的地址
```java
House copyRef = blueHouse;
```

如果有多個 reference 指向同一物件
改變其中一個，其他 reference 也會看到改變

沒有 reference 指向的物件，Java 會自動清理