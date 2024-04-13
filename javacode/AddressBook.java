import java.io.*;
import java.util.*;

class Contact implements Serializable {
    private String name;
    private String phoneNumber;
    private String emailAddress;

    public Contact(String name, String phoneNumber, String emailAddress) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone Number: " + phoneNumber + ", Email Address: " + emailAddress;
    }

    
    public String serialize() {
        return name + "," + phoneNumber + "," + emailAddress;
    }

    
    public static Contact deserialize(String data) {
        String[] parts = data.split(",");
        return new Contact(parts[0], parts[1], parts[2]);
    }
}

class AddressBook {
    private static final String DATABASE_FILE = "contacts_database.txt";
    private List<Contact> contacts;
    private Scanner scanner;

    public AddressBook() {
        contacts = new ArrayList<>();
        scanner = new Scanner(System.in);
        loadContacts();
    }

    public void menu() {
        System.out.println("Address Book System");
        System.out.println("1. Add Contact");
        System.out.println("2. Remove Contact");
        System.out.println("3. Search Contact");
        System.out.println("4. Display All Contacts");
        System.out.println("5. Exit");

        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                addContact();
                break;
            case 2:
                removeContact();
                break;
            case 3:
                searchContact();
                break;
            case 4:
                displayAllContacts();
                break;
            case 5:
                saveContacts();
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
        menu();
    }

    private void addContact() {
        System.out.println("Enter contact details:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Email Address: ");
        String emailAddress = scanner.nextLine();

        Contact contact = new Contact(name, phoneNumber, emailAddress);
        contacts.add(contact);
        System.out.println("Contact added successfully.");
    }

    private void removeContact() {
        System.out.print("Enter the name of the contact to remove: ");
        String name = scanner.nextLine();

        boolean found = false;
        Iterator<Contact> iterator = contacts.iterator();
        while (iterator.hasNext()) {
            Contact contact = iterator.next();
            if (contact.getName().equalsIgnoreCase(name)) {
                iterator.remove();
                found = true;
                break;
            }
        }

        if (found) {
            System.out.println("Contact removed successfully.");
        } else {
            System.out.println("Contact with name '" + name + "' not found.");
        }
    }

    private void searchContact() {
        System.out.print("Enter name to search: ");
        String name = scanner.nextLine();

        boolean found = false;
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                System.out.println("Contact details:");
                System.out.println(contact);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Contact with name '" + name + "' not found.");
        }
    }

    private void displayAllContacts() {
        if (!contacts.isEmpty()) {
            System.out.println("All Contacts:");
            for (Contact contact : contacts) {
                System.out.println(contact);
            }
        } else {
            System.out.println("No contacts available.");
        }
    }

    private void loadContacts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATABASE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contacts.add(Contact.deserialize(line));
            }
            System.out.println("Contacts loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("No existing contact data found. Starting with an empty contact list.");
        } catch (IOException e) {
            System.out.println("Error loading contact data: " + e.getMessage());
        }
    }

    private void saveContacts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATABASE_FILE))) {
            for (Contact contact : contacts) {
                writer.write(contact.serialize() + "\n");
            }
            System.out.println("Contacts saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving contact data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook();
        addressBook.menu();
    }
}
