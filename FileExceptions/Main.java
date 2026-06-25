package FileExceptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("Current Working Directory (cwd) = " +
                new File("").getAbsolutePath());

        String filename = "files/testing.csv";

        File file = new File(
                new File("").getAbsolutePath(),
                filename);

        System.out.println(file.getAbsolutePath());

        if (!file.exists()) {
            System.out.println("I can't run unless this file exists");
            return;
        }

        System.out.println("I'm good to go.");

        for (File f : File.listRoots()) {
            System.out.println(f);
        }

        Path path = Paths.get("files/testing.csv");

        System.out.println(file.getAbsolutePath());

        if (!Files.exists(path)) {
            System.out.println("2. I can't run unless this file exists");
            return;
        }

        System.out.println("2. I'm good to go.");
    }

    private static void testFile(String filename) {

        Path path = Paths.get(filename);

        try {
            /* 嘗試讀取檔案的所有行數，可能會拋出 IOException */
            List lines = Files.readAllLines(path);

        } catch (IOException e) {

            /* 擷取到 Checked Exception 後，將其包裝成執行期例外 RuntimeException 重新拋出 */
            throw new RuntimeException(e);

        } finally {

            /* 無論有無發生例外，或者是例外在哪個區塊被拋出，finally 區塊的程式碼保證一定會執行 */
            System.out.println("Maybe I'd log something either way...");
        }

        /* 如果前面的例外沒有被處理或重新拋出，這行之後的程式碼將不會執行 */
        System.out.println("File exists and able to use as a resource");
    }
}