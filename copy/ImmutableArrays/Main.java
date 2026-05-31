package copy.ImmutableArrays;

import java.util.Arrays;

/**
 * 
 * 原本看似不可變的 `record`
 * 因為內含了可變的陣列欄位（`Person[] kids`）
 * 展示了各種拷貝方式對記憶體引用（Reference）與副作用（Side Effects）的實質影響
 * 
 * Record：表面 immutable，但遇到「可變型別欄位」仍可能被破壞
 */
record Person(String name, String dob, Person[] kids) {

    // =====================================================
    // Copy Constructor（防禦性複製版本）
    // =====================================================

    public Person(Person p) {

        this(
                p.name,
                p.dob,
                p.kids == null
                        ? null
                        : Arrays.copyOf(p.kids, p.kids.length));
    }

    // =====================================================
    // toString
    // =====================================================

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", kids=" + Arrays.toString(kids) +
                '}';
    }
}

public class Main {

    public static void main(String[] args) {

        // =====================================================
        // 基本資料建立
        // =====================================================

        Person joe = new Person("Joe", "01/01/1961", null);
        Person jim = new Person("Jim", "02/02/1962", null);

        Person jack = new Person(
                "Jack",
                "03/03/1963",
                new Person[] { joe, jim });

        Person jane = new Person("Jane", "04/04/1964", null);

        Person jill = new Person(
                "Jill",
                "05/05/1965",
                new Person[] { joe, jim });

        // 原始陣列
        Person[] persons = { joe, jim, jack, jane, jill };

        // =====================================================
        // 情境 A：clone（Shallow Copy）
        // =====================================================

        Person[] personsCopy = persons.clone();

        // =====================================================
        // 情境 B：Arrays.copyOf（同樣 Shallow Copy）
        // =====================================================

        // Person[] personsCopy = Arrays.copyOf(persons, persons.length);

        // =====================================================
        // 情境 C：Deep Copy（正確做法）
        // =====================================================

        // Person[] personsCopy = new Person[persons.length];
        // Arrays.setAll(personsCopy, i -> new Person(persons[i]));

        // =====================================================
        // 副作用測試（Side Effect）
        // =====================================================

        var jillsKids = personsCopy[4].kids();

        // ❌ 修改 copy 出來的 array
        jillsKids[1] = jane;

        // =====================================================
        // 驗證 1：reference 是否相同
        // =====================================================

        for (int i = 0; i < persons.length; i++) {

            if (persons[i] == personsCopy[i]) {
                System.out.println("Equal References " + persons[i]);
            }
        }

        // =====================================================
        // 驗證 2：觀察 side effect
        // =====================================================

        System.out.println(persons[4]);
        System.out.println(personsCopy[4]);
    }
}