package Hashing.contact;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        List<Contact> emails = ContactData.getData("email");
        List<Contact> phones = ContactData.getData("phone");

        printData("Phone List", phones);
        printData("Email List", emails);

        // ❌ 原本你這裡寫 Set = getData（錯）
        Set<Contact> emailContacts = new HashSet<>(emails);
        Set<Contact> phoneContacts = new HashSet<>(phones);

        int index = emails.indexOf(new Contact("Robin Hood"));
        Contact robinHood = emails.get(index);

        robinHood.addEmail("Sherwood Forest");
        robinHood.addEmail("Sherwood Forest"); // duplicate test

        robinHood.replaceEmailIfExists(
                "RHood@sherwoodforest.com",
                "RHood@sherwoodforest.org");

        System.out.println(robinHood);

        // union
        Set<Contact> unionAB = new HashSet<>();
        unionAB.addAll(emailContacts);
        unionAB.addAll(phoneContacts);

        System.out.println("----------------------------------------------");
        System.out.println("Union Set");
        System.out.println("----------------------------------------------");

        unionAB.forEach(System.out::println);
    }

    public static void printData(String header, Collection<Contact> contacts) {

        System.out.println("----------------------------------------------");
        System.out.println(header);
        System.out.println("----------------------------------------------");

        contacts.forEach(System.out::println);
    }
}