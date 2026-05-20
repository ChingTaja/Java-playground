package Hashing.CollectionInterface;

import java.util.HashSet;
import java.util.Set;

public class Contact {

    private String name;
    private Set<String> emails = new HashSet<>();
    private Set<String> phones = new HashSet<>();

    public Contact(String name) {
        this(name, null, 0);
    }

    public Contact(String name, String email) {
        this(name, email, 0);
    }

    public Contact(String name, long phone) {
        this(name, null, phone);
    }

    public Contact(String name, String email, long phone) {

        this.name = name;

        if (email != null) {
            emails.add(email);
        }

        if (phone > 0) {
            String p = String.valueOf(phone);

            p = "(%s) %s-%s".formatted(
                    p.substring(0, 3),
                    p.substring(3, 6),
                    p.substring(6));

            phones.add(p);
        }
    }

    public Contact(Contact other) {
        this.name = other.name;
        this.emails = new HashSet<>(other.emails);
        this.phones = new HashSet<>(other.phones);
    }

    public String getNameLastFirst() {
        return name.substring(name.indexOf(" ") + 1) + ", " +
                name.substring(0, name.indexOf(" "));
    }

    public String getName() {
        return name;
    }

    public void addEmail(String companyName) {

        String[] names = name.trim().split("\\s+");

        String email = "%c%s@%s.com".formatted(
                name.charAt(0),
                names[names.length - 1],
                companyName.replaceAll(" ", "").toLowerCase());

        if (!emails.add(email)) {
            System.out.println(name + " already has email " + email);
        } else {
            System.out.println(name + " now has email " + email);
        }
    }

    public void replaceEmailIfExists(String oldEmail, String newEmail) {

        if (emails.remove(oldEmail)) {
            emails.add(newEmail);
        } else {
            System.out.println("Email not found: " + oldEmail);
        }
    }

    public Contact mergeContactData(Contact contact) {

        Contact newContact = new Contact(this);

        newContact.emails.addAll(contact.emails);
        newContact.phones.addAll(contact.phones);

        return newContact;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Contact contact))
            return false;

        return name != null &&
                name.trim().equalsIgnoreCase(contact.name.trim());
    }

    @Override
    public int hashCode() {
        return name.trim().toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return """
                %s
                Emails: %s
                Phones: %s
                """.formatted(name, emails, phones);
    }
}