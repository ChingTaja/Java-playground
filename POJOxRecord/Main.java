package POJOxRecord;
// contrived example
public class Main {
    public static void main(String[] args) {
        // 建立五個學生物件
        for (int i = 1; i <= 5; i++) {
            String id = "S92300" + i;
            String name;
            switch (i) {
                case 1 -> name = "Mary";
                case 2 -> name = "Carol";
                case 3 -> name = "Tim";
                case 4 -> name = "Harry";
                case 5 -> name = "Lisa";
                default -> name = "Anonymous";
            }
            LAPStudent s = new LAPStudent(id, name, "1985-11-05", "Java Masterclass");
            System.out.println(s);
            // 每個物件傳給 println 時，如果該類別有實作 toString() 方法，Java 會自動執行它
        }
        Student pojoStudent = new Student("W923006", "Ann", "5 November 1985", "Java Masterclass");
        
        LAPStudent recordStudent = new LAPStudent("S923007", "Bill", "5 November 1985", "Java Masterclass");
        
        System.out.println(pojoStudent);
        System.out.println(recordStudent);

        // record 不使用 get 前綴
        // accessor 方法名稱直接使用元件名稱
        System.out.println(pojoStudent.getName() + " is taking " + pojoStudent.getClassList());
        System.out.println(recordStudent.name() + " is taking " + recordStudent.classList());

        pojoStudent.setClassList(pojoStudent.getClassList() + ", Java OCP Exam 829");
        
        // recordStudent.setClassList(recordStudent.classList() + ", Java OCP Exam 829"); //!! 錯誤
    }
}