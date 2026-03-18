package StringExample;

public class StringBuilderExample {

    public static void main(String[] args) {

        String helloWorld = "Hello" + "World";

        // ❌ 不會改變原字串（String immutable）
        helloWorld.concat(" and Goodbye");

        // ✅ 要重新指定
        helloWorld = helloWorld.concat(" and Goodbye");

        // StringBuilder（mutable）
        StringBuilder helloWorldBuilder = new StringBuilder("Hello" + "World");
        helloWorldBuilder.append(" and Goodbye");

        printInformation(helloWorld);
        printInformation(helloWorldBuilder);

        // 預設 capacity = 16 + length
        StringBuilder emptyStart = new StringBuilder();
        emptyStart.append("a".repeat(18));

        // 指定 capacity
        StringBuilder emptyStart32 = new StringBuilder(32);
        emptyStart32.append("a".repeat(18));

        printInformation(emptyStart);
        printInformation(emptyStart32);

        // 操作字串
        StringBuilder builderPlus = new StringBuilder("Hello" + "World");
        builderPlus.append(" and Goodbye");

        // 改字元（delete + insert）
        builderPlus.deleteCharAt(16).insert(16, 'g');
        System.out.println(builderPlus);

        // replace（注意是 String 不是 char）
        builderPlus.replace(16, 17, "G");
        System.out.println(builderPlus);

        // reverse + setLength
        builderPlus.reverse().setLength(7);
        System.out.println(builderPlus);
    }

    public static void printInformation(String string) {
        System.out.println("String = " + string);
        System.out.println("length = " + string.length());
    }

    public static void printInformation(StringBuilder builder) {
        System.out.println("StringBuilder = " + builder);
        System.out.println("length = " + builder.length());
        System.out.println("capacity = " + builder.capacity());
    }
}