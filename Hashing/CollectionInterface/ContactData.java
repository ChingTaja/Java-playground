package Hashing.CollectionInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactData {

    private static final String phoneData = """
            Charlie Brown, 3334445555
            Maid Marion, 1234567890
            Mickey Mouse, 9998887777
            Mickey Mouse, 1247489758
            Minnie Mouse, 4567805666
            Robin Hood, 5647893000
            Robin Hood, 7899028222
            Lucy Van Pelt, 5642086852
            Mickey Mouse, 9998887777
            """;

    private static final String emailData = """
            Mickey Mouse, mckmouse@gmail.com
            Mickey Mouse, micky1@aws.com
            Minnie Mouse, minnie@verizon.net
            Robin Hood, rhood@gmail.com
            Linus Van Pelt, lvpelt2015@gmail.com
            Daffy Duck, daffy@google.com
            """;

    public static List<Contact> getData(String type) {

        List<Contact> dataList = new ArrayList<>();

        String source = type.equalsIgnoreCase("phone") ? phoneData : emailData;

        try (Scanner scanner = new Scanner(source)) {

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine().trim();

                if (line.isEmpty())
                    continue;

                String[] data = line.split(",");

                String name = data[0].trim();

                if (type.equalsIgnoreCase("phone")) {

                    long phone = Long.parseLong(data[1].trim());
                    dataList.add(new Contact(name, phone));

                } else {

                    String email = data[1].trim();
                    dataList.add(new Contact(name, email));
                }
            }
        }

        return dataList;
    }
}