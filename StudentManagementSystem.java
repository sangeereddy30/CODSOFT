import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentManagementSystem {
    private List<Student> students;
    private static final String FILE_NAME = "students.dat";

    public StudentManagementSystem() {
        students = loadStudentsFromFile();
    }

    public void addStudent(Student student) {
        students.add(student);
        saveStudentsToFile();
    }

    public void removeStudent(String rollNumber) {
        students.removeIf(student -> student.getRollNumber().equals(rollNumber));
        saveStudentsToFile();
    }

    public Student searchStudent(String rollNumber) {
        return students.stream()
                .filter(student -> student.getRollNumber().equals(rollNumber))
                .findFirst()
                .orElse(null);
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students to display.");
        } else {
            students.forEach(System.out::println);
        }
    }

    private void saveStudentsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving students to file: " + e.getMessage());
        }
    }

    private List<Student> loadStudentsFromFile() {
        List<Student> studentsList = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            studentsList = (List<Student>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No previous data found, starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading students from file: " + e.getMessage());
        }
        return studentsList;
    }

    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nStudent Management System");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Edit Student");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter roll number: ");
                    String rollNumber = scanner.nextLine();
                    System.out.print("Enter grade: ");
                    String grade = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    if (name.isEmpty() || rollNumber.isEmpty() || grade.isEmpty() || email.isEmpty()) {
                        System.out.println("All fields are required.");
                    } else {
                        Student student = new Student(name, rollNumber, grade, email);
                        sms.addStudent(student);
                        System.out.println("Student added successfully.");
                    }
                    break;
                case 2:
                    System.out.print("Enter roll number to remove: ");
                    rollNumber = scanner.nextLine();
                    sms.removeStudent(rollNumber);
                    System.out.println("Student removed successfully.");
                    break;
                case 3:
                    System.out.print("Enter roll number to search: ");
                    rollNumber = scanner.nextLine();
                    Student student = sms.searchStudent(rollNumber);
                    if (student != null) {
                        System.out.println("Student found: " + student);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 4:
                    sms.displayAllStudents();
                    break;
                case 5:
                    System.out.print("Enter roll number to edit: ");
                    rollNumber = scanner.nextLine();
                    student = sms.searchStudent(rollNumber);
                    if (student != null) {
                        System.out.print("Enter new name (or press Enter to keep current): ");
                        name = scanner.nextLine();
                        if (!name.isEmpty()) student.setName(name);

                        System.out.print("Enter new grade (or press Enter to keep current): ");
                        grade = scanner.nextLine();
                        if (!grade.isEmpty()) student.setGrade(grade);

                        System.out.print("Enter new email (or press Enter to keep current): ");
                        email = scanner.nextLine();
                        if (!email.isEmpty()) student.setEmail(email);

                        sms.saveStudentsToFile();
                        System.out.println("Student information updated successfully.");
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 6:
                    System.out.println("Exiting the system.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
