import java.io.*;
import java.util.*;

class Student implements Serializable {
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Roll Number: " + rollNumber + ", Grade: " + grade;
    }

    public String serialize() {
        return name + "," + rollNumber + "," + grade;
    }

    public static Student deserialize(String data) {
        String[] parts = data.split(",");
        return new Student(parts[0], Integer.parseInt(parts[1]), parts[2]);
    }
}

class StudentManagementSystem {
    private static final String DATA_FILE = "student_data.txt";
    private static final String DATABASE_FILE = "student_database.txt"; // Database file
    private List<Student> students;
    private Scanner scanner;

    public StudentManagementSystem() {
        students = new ArrayList<>();
        scanner = new Scanner(System.in);
        loadStudents();
    }

    public void menu() {
        System.out.println("Student Management System");
        System.out.println("1. Add Student");
        System.out.println("2. Remove Student");
        System.out.println("3. Search Student");
        System.out.println("4. Display All Students");
        System.out.println("5. Exit");

        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                addStudent();
                break;
            case 2:
                removeStudent();
                break;
            case 3:
                searchStudent();
                break;
            case 4:
                displayAllStudents();
                break;
            case 5:
                saveStudents();
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
        menu();
    }

    private void addStudent() {
        System.out.println("Enter student details:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Roll Number: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Grade: ");
        String grade = scanner.nextLine();

        Student student = new Student(name, rollNumber, grade);
        students.add(student);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATABASE_FILE, true))) {
            writer.write(student.serialize() + "\n");
            System.out.println("Student added successfully.");
        } catch (IOException e) {
            System.out.println("Error adding student to database: " + e.getMessage());
        }
    }

    private void removeStudent() {
        System.out.print("Enter the roll number of the student to remove: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine(); 

        boolean found = false;
        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.getRollNumber() == rollNumber) {
                iterator.remove();
                found = true;
                break;
            }
        }

        if (found) {
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student with roll number " + rollNumber + " not found.");
        }
    }

    private void searchStudent() {
        System.out.print("Enter roll number to search: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine(); 

        boolean found = false;
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                System.out.println("Student details:");
                System.out.println(student);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Student with roll number " + rollNumber + " not found.");
        }
    }

    private void displayAllStudents() {
        if (!students.isEmpty()) {
            System.out.println("All Students:");
            for (Student student : students) {
                System.out.println(student);
            }
        } else {
            System.out.println("No students available.");
        }
    }

    private void loadStudents() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                students.add(Student.deserialize(line));
            }
            System.out.println("Students loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("No existing student data found. Starting with an empty student list.");
        } catch (IOException e) {
            System.out.println("Error loading student data: " + e.getMessage());
        }
    }

    private void saveStudents() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (Student student : students) {
                writer.write(student.serialize() + "\n");
            }
            System.out.println("Students saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving student data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();
        sms.menu();
    }
}