
# Project Description

The Charity is a web application designed to facilitate the donation of various items to selected foundations. The application allows users to manage their donations, user accounts, and offers a comprehensive administration panel for managing users, donations, categories, and foundations.


### Features

#### User Features
- User registration (confirmation by e-mail)
- Donate Items: Users can donate various items to selected foundations.
- User Account Management: Full CRUD (Create, Read, Update, Delete) operations for managing user accounts.
- Donation Management: Full CRUD operations for managing donations, including marking donations as received.

#### Admin Features
- Admin Management: Manage admin accounts with full CRUD operations.
- User Management: Admins can manage user accounts with full CRUD operations.
- Donation Management: Admins have full control over donations, including viewing, updating, and deleting donations.
- Category Management: Manage donation categories with full CRUD operations.
- Foundation Management: Manage foundations with full CRUD operations.

### Technologies Used

- Spring Boot 3+
- Java 17 (or later)
- JSP for frontend templating
- Spring Security for authentication and authorization
- Spring Data JPA for data persistence
- Spring Boot Mail
- MySQL for the database
- Hibernate as the ORM framework
- Bootstrap for responsive design
- JavaScript/jQuery for dynamic functionalities




## Installation

#### Spring Security
Spring Security is configured to protect application resources. Users and roles are defined in the database.

#### Install my-project with npm

```bash
git clone https://github.com/Jaro95/Charity.git
cd charity-donation-app
```

### File: `application.properties` 

Below are the key configuration properties:
```properties
# MySQL Database Configuration
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/charity
spring.datasource.username= add your username
spring.datasource.password= add your password
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true;

# Mail Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=add your username
spring.mail.password=add your password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

If you do not want to use e-mail verification, change the value in UserServiceImpl.class in line 47 to true and in line 48 to "verified".

### Run

Paste link http://localhost:8080/charity/admin/create-start you create 2 users(super admin and admin - email and password are in AdminController.class 37-49 lines) and elements necessary for the proper functioning of the application.



    