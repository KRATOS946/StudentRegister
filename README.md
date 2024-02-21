Description:-
This is a simple Java console application for managing student information using JDBC (Java Database Connectivity) with a MySQL database. The application allows users to perform CRUD (Create, Read, Update, Delete) operations on student records.

Features:-
Establishes a connection to a MySQL database.
Allows the user to create a student table if it doesn't exist.
Supports operations like adding a new student, updating student details, deleting a student, and viewing all student details.
Uses PreparedStatement to interact with the database, preventing SQL injection.

Prerequisites:-
Java Development Kit (JDK)
MySQL Database
MySQL Connector/J (JDBC Driver)

Usage:-
Upon running the application, you will be prompted to enter MySQL database credentials and table name.

Choose an operation by entering the corresponding number:

1: Add a new student
2: Update student details
3: Delete a student
4: View all student details
Follow the prompts to complete the chosen operation.

After each operation, you will be asked if you want to perform another operation.
