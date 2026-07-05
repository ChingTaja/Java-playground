package WritingFiles;

import WritingFiles.student.Course;
import WritingFiles.student.Student;

import java.io.BufferedWriter;

import java.io.FileWriter;

import java.io.IOException;

import java.io.PrintWriter;

import java.nio.file.Files;

import java.nio.file.Path;

import java.util.List;

import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        String header = """
                Student Id,Country Code,Enrolled Year,Age,Gender,\
                Experienced,Course Code,Engagement Month,Engagement Year,\
                Engagement Type""";
        Course jmc = new Course("JMC", "Java Masterclass");
        Course pymc = new Course("PYC", "Python Masterclass");
        List<Student> students = Stream
                .generate(() -> Student.getRandomStudent(jmc, pymc))
                .limit(25)
                .toList();
        // System.out.println(header);
        // students.forEach(s -> s.getEngagementRecords().forEach(System.out::println));
        Path path = Path.of("students.csv");
        // try {
        // Files.writeString(path, header);
        // for (Student student : students) {
        // Files.write(path, student.getEngagementRecords(),
        // StandardOpenOption.APPEND);
        // }
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // try {
        // List data = new ArrayList<>();
        // data.add(header);
        // for (Student student : students) {
        // data.addAll(student.getEngagementRecords());
        // }
        // Files.write(path, data);
        // } catch (IOException e) {
        // e.printStackTrace();
        // }

        /*
         * 區塊一：使用 BufferedWriter 進行有緩衝處理的檔案寫入
         * 適用於寫入大量文字資料，能減少實體磁碟存取次數
         */
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of("take2.csv"))) {
            writer.write(header);
            /* 呼叫 newLine 方法來寫入換行符號 */
            writer.newLine();
            int count = 0;
            for (Student student : students) {
                for (var record : student.getEngagementRecords()) {
                    writer.write(record);
                    writer.newLine();
                    count++;

                    /*
                     * 模擬應用程式處理延遲
                     * 每處理 5 筆紀錄就讓執行緒暫停 2 秒鐘
                     */
                    if (count % 5 == 0) {
                        Thread.sleep(2000);
                        System.out.print(".");
                    }

                    /*
                     * 實作手動清除緩衝區機制
                     * 每隔 10 筆紀錄就強制將資料實體寫入磁碟，確保資料即時性
                     */
                    if (count % 10 == 0) {
                        writer.flush();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        /*
         * 區塊二：使用 FileWriter 進行常規檔案寫入
         * 內部緩衝區非常小且大小未受 Java 保證，適用於少量資料寫入
         * 不支援 newLine 方法，需手動串接系統換行符號
         */
        try (FileWriter writer = new FileWriter("take3.csv")) {
            writer.write(header);
            /* 使用 System.lineSeparator 獲取符合當前作業系統規範的換行字元 */
            writer.write(System.lineSeparator());
            for (Student student : students) {
                for (var record : student.getEngagementRecords()) {
                    writer.write(record);
                    writer.write(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * 區塊三：使用 PrintWriter 進行格式化檔案寫入
         * 提供直覺的 println、printf、format 等高階格式化方法
         * 傳入字串路徑的建構子底層會自動包覆 BufferedWriter，但預設會關閉 autoFlush
         */
        try (PrintWriter writer = new PrintWriter("take4.txt")) {
            /* 使用 println 會在輸出文字後自動補上作業系統預設的換行符號 */
            writer.println(header);
            for (Student student : students) {
                for (var record : student.getEngagementRecords()) {
                    String[] recordData = record.split(",");

                    /*
                     * 使用 printf 進行固定長度（Fixed length）欄位格式化輸出
                     * %-12d 代表左對齊且寬度為 12 的整數欄位
                     * 通常文字欄位習慣靠左對齊，數值欄位習慣靠右對齊
                     */
                    writer.printf("%-12d%-5s%2d%4d%3d%-1s".formatted(
                            student.getStudentId(), // Student Id
                            student.getCountry(), // Country Code
                            student.getEnrollmentYear(), // Enrolled Year
                            student.getEnrollmentMonth(), // Enrolled Month
                            student.getEnrollmentAge(), // Age
                            student.getGender())); // Gender

                    /* 透過三元運算子轉換布林值為字元標記 */
                    writer.printf("%-1s",
                            (student.hasExperience() ? 'Y' : 'N')); // Experienced?

                    /* format 方法與 printf 具備完全相同的作用，可互換使用 */
                    writer.format("%-3s%10.2f%-10s%-4s%-30s",
                            recordData[7], // Course Code
                            student.getPercentComplete(recordData[7]),
                            recordData[8], // Engagement Month
                            recordData[9], // Engagement Year
                            recordData[10]); // Engagement Type

                    /* 呼叫不帶引數的 println 方法來產出換行效果 */
                    writer.println();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}