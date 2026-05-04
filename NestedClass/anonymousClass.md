nonymous class 是沒名字的 local class

# 特色
1. 沒有 class 名稱
2. 直接「邊寫邊 new」
3. 只能用一次

```java
Runnable r = new Runnable() {
    @Override
    public void run() {
        System.out.println("Hello");
    }
};
```

# 為什麼現在比較少用？

因為 Lambda 更簡潔
```java
Runnable r = () -> System.out.println("Hello");
```

# 還是要學的原因

✔ 舊 code 還會看到
✔ 某些情況 lambda 做不到
✔ 幫助理解 JVM / functional interface