# 📚 Library Management System

A full-featured Library Management System built using **Java**, **JSP/Servlets**, and **MySQL** to streamline book inventory, borrowing, and member management.

---

## 📌 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Database Schema](#database-schema)
- ![Database Schema](pictures/schema.png)
- [Setup Instructions](#setup-instructions)
- [Screenshots](#screenshots)
- [Contributing](#contributing)
- [License](#license)

---

## Features

- 📘 Add, update, and delete books
- 👥 Manage library members
- 📖 Issue and return books
- 🔎 Search functionality for books and members
- 📊 View issued books and history
- 🛡️ Login authentication for admin/librarian

---

## Tech Stack

- **Backend:** Java, Servlet, JSP
- **Frontend:** HTML, CSS, JavaScript (basic)
- **Database:** MySQL
- **Tools:** Apache Tomcat, JDBC, Git

---

## Project Structure

```plaintext
LibraryManagementSystem/
│
├── src/
│   ├── com.library.controller/       # Servlet controllers
│   ├── com.library.dao/              # Data Access Objects (DB logic)
│   ├── com.library.model/            # JavaBeans/POJOs
│
├── WebContent/
│   ├── css/                          # Stylesheets
│   ├── js/                           # JavaScript files
│   ├── images/                       # Assets
│   ├── pages/                        # JSP pages
│   └── index.jsp                     # Home / Login page
│
├── lib/                              # External JARs (MySQL Connector etc.)
├── .classpath
├── .project
└── README.md
```
## Database Schema

📂 Database Schema
The application uses a MySQL database named librarymanagement which contains the following main tables:

admin – Stores administrator login credentials.

books – Contains information about all the books in the library.

readers – Stores data about library members.

issuebook – Tracks which books are issued to which readers, along with issue and return dates.

issuerequest – Logs requests made by readers to borrow books.
