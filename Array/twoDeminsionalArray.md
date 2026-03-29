- Array Initializers
```java
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};
```

- Jagged Arrays
```java
// 只指定有 3 列，每列長度先不決定
int[][] jagged = new int[3][]; 

jagged[0] = new int[2]; // 第一列長度為 2
jagged[1] = new int[5]; // 第二列長度為 5
jagged[2] = new int[3]; // 第三列長度為 3
```