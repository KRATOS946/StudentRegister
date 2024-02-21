/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication11;

import java.util.*;
import java.sql.*;
/**
 *
 * @author Akash kumar
 */
public class JavaApplication11 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws Exception {
        Scanner sc = new Scanner(System.in);
        
        String url = "jdbc:mysql://localhost:3306/jdbc_db";
        System.out.println("Enter your username(mysql) : ");
        String user = sc.nextLine();
        System.out.println("Enter your password(mysql) : ");
        String password = sc.nextLine();
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        Connection con = DriverManager.getConnection(url,user,password);
        System.out.println("Connection created successfully!");
        
        System.out.println("Enter table name : ");
        String tableName = sc.nextLine();
        
        if (!tableExists(con, tableName)) {
                createTable(con, tableName);
            } else {
                System.out.println("Table already exists. Skipping table creation.");
            }
        System.out.println("-------------------------");
        
        while (true) {
            System.out.println("Enter operation to perform : \nTo enter details of new student ENTER 1 \nTo UPDATE student details ENTER 2 \nTo DELETE student details ENTER 3 \nTo VIEW student details ENTER 4 : ");
            System.out.println("-------------------------");

            int operation = sc.nextInt();
            sc.nextLine(); // Consume the newline character

            switch (operation) {
                case 1:
                    System.out.println("Enter details for registration of new student!");
                    addNewStudent(con);
                    break;
                case 2:
                    updateStudentDetails(con);
                    break;
                case 3:
                    deleteStudent(con);
                    break;
                case 4:
                    viewStudentDetails(con);
                    break;
                default:
                    System.out.println("Invalid option");
            }

            System.out.println("Do you want to perform another operation? (Y/N): ");
            String continueChoice = sc.nextLine().trim();
            if (!continueChoice.equalsIgnoreCase("Y")) {
                System.out.println("Exiting program. Goodbye!");
                break; // Exit the loop if the user chooses not to continue
            }
        }
    }
    
    private static boolean tableExists(Connection con, String tableName)throws Exception{
        DatabaseMetaData meta = con.getMetaData();
        try (ResultSet rs = meta.getTables(null, null, tableName, new String[]{"TABLE"}))
        {
            return rs.next();
        }
    }
    
    private static void createTable(Connection con, String tableName) throws SQLException {
        try (Statement statement = con.createStatement()) {
            String createTableQuery = "CREATE TABLE " + tableName + " ("
                    + "Sid INT PRIMARY KEY AUTO_INCREMENT,"
                    + "name VARCHAR(255),"
                    + "email VARCHAR(255),"
                    + "password VARCHAR(100),"
                    + "gender VARCHAR(10),"
                    + "city VARCHAR(255))";
            statement.executeUpdate(createTableQuery);
            System.out.println("Table created successfully.");
        }
    }
    
    private static void addNewStudent(Connection con)throws SQLException {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Enter name of the student : ");
        String name = sc.nextLine();
        System.out.println("Enter email of the student : ");
        String email = sc.nextLine();
        System.out.println("Enter password of the student : ");
        String password = sc.nextLine();
        System.out.println("Enter gender of the student : ");
        String gender = sc.nextLine();
        System.out.println("Enter city of the student : ");
        String city = sc.nextLine();
        
        PreparedStatement ps = con.prepareStatement("INSERT INTO STUDENTS (name, email, password, gender, city) values(?,?,?,?,?)");
        
        ps.setString(1, name);
        ps.setString(2, email);
        ps.setString(3, password);
        ps.setString(4, gender);
        ps.setString(5, city);
        
        int i = ps.executeUpdate();
        if(i>0){
            System.out.println("Student registered successfully!");
        }
        else{
            System.out.println("Registration failed");
        }
    }

    private static void updateStudentDetails(Connection con)throws SQLException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the student's name to update details: ");
        String name = sc.nextLine();

        System.out.println("Select the element to update:");
        System.out.println("1. Email");
        System.out.println("2. Password");
        System.out.println("3. Gender");
        System.out.println("4. City");

        int choice = sc.nextInt();
        sc.nextLine(); // Consume the newline character

        String columnName = "";
        String newValue = "";

        switch (choice) {
            case 1:
                columnName = "email";
                System.out.println("Enter new email: ");
                newValue = sc.nextLine();
                break;
            case 2:
                columnName = "password";
                System.out.println("Enter new password: ");
                newValue = sc.nextLine();
                break;
            case 3:
                columnName = "gender";
                System.out.println("Enter new gender: ");
                newValue = sc.nextLine();
                break;
            case 4:
                columnName = "city";
                System.out.println("Enter new city: ");
                newValue = sc.nextLine();
                break;
            default:
                System.out.println("Invalid option");
                return; // Exit the method if an invalid option is selected
        }
        PreparedStatement ps = con.prepareStatement("update students set "+ columnName +"=? where name=?");
            // Set the values for the parameters
        ps.setString(1, newValue);
        ps.setString(2, name);

        int rowsAffected = ps.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Student details updated successfully!");
        } else {
            System.out.println("Failed to update student details. Student not found.");
        }
    }

    private static void deleteStudent(Connection con)throws SQLException {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Enter name of the student for deletion : ");
        String name = sc.nextLine();
        
        System.out.println("Enter email of the Student : ");
        String email = sc.nextLine();
        System.out.println("Enter password for authentication : ");
        String password = sc.nextLine();
        
        PreparedStatement ps = con.prepareStatement("delete from students where email=? and password=?");
        ps.setString(1,email);
        ps.setString(2, password);
        
        int i = ps.executeUpdate();
        if(i>0)
        {
            System.out.println("Deletion Successfull");
        }
        else{
            System.out.println("Deletion failed");
        }
    }

    private static void viewStudentDetails(Connection con)throws SQLException {
        PreparedStatement ps = con.prepareStatement("select * from students");
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            System.out.print("Name : "+rs.getString("name"));
            System.out.print("|| Email : "+rs.getString("email"));
            System.out.print(" Password : "+rs.getString("password"));
            System.out.print(" Gender : "+rs.getString("gender"));
            System.out.print(" City : "+rs.getString("city"));
            System.out.println("\n-----------------------------");
        }
    }
}

