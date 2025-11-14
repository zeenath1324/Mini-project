 Library Management System – Java Console Application
 
 Overview:
This Library Management System is a **Java console-based application** that helps manage books, members, issuing and returning operations.
It uses **Object-Oriented Programming**, **Collections**, **Custom Exceptions**, and **File Handling**.

Features:

* Add new books
* Add library members
* Issue books to members
* Return books with late fee calculation
* Track which books a member has borrowed
* View complete library inventory
* Log every operation into a file (`library_log.txt`)
* Handles invalid operations with custom exceptions

Technologies Used

* Java (JDK 8+)
* Collections Framework
* FileWriter for logging
* Custom Exception Handling

How to Run the Program

1. Compile the program

   javac LibrarySystem.java

2. Run the program

java LibrarySystem

 Project Structure

LibrarySystem/
│
├── Book.java
├── Member.java
├── Library.java
├── BookNotAvailableException.java
├── InvalidReturnException.java
├── LibrarySystem.java
└── library_log.txt   (generated automatically)

Sample Output
===== LIBRARY MENU =====
1. Add Book
2. Add Member
3. Issue Book
4. Return Book
5. Show Inventory
6. Exit
Enter your choice:


Book Return Example:

Enter Days Late: 3
Book returned successfully! Late Fee: ₹6


 Logging (library_log.txt)

Example logs generated:

Wed Nov 14 10:21:18 IST 2025 - Added Book: Book[ID=B101, Title=Java, Author=Gosling, Status=Available]
Wed Nov 14 10:22:11 IST 2025 - Issued Book: B101 to Member: M001
Wed Nov 14 10:25:03 IST 2025 - Returned Book: B101 by Member: M001 | Late fee: 

Class Overview

Book Class

* id
* title
* author
* isIssued

Member Class

* memberId
* name
* borrowedBookIds

Library Class

* inventory
* members
* log file handling
* issue / return logic

Exceptions

* BookNotAvailableException
* InvalidReturnException


 Future Enhancements

* Add search functionality
* Add due dates and reminders
* GUI using JavaFX
* Connect with database (MySQL)


 Author

Zeenath Nisha
B.Tech IT — Mohamed Sathak A.J. College of Engineering
Chennai, India





