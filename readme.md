
# Charity Donation Application

The Charity is a web application designed to facilitate the donation of various items to selected foundations. The application allows users to manage their donations, user accounts, and offers a comprehensive administration panel for managing users, donations, categories, and foundations.


## Features

### User Features
- **User Registration**: Confirmation by e-mail.
- **Donate Items**: Users can donate various items to selected foundations.
- **User Account Management**: Full CRUD (Create, Read, Update, Delete) operations for managing user accounts.
- **Donation Management**: Full CRUD operations for managing donations, including marking donations as received.

### Admin Features
- **Admin Management**: Manage admin accounts with full CRUD operations.
- **User Management**: Admins can manage user accounts with full CRUD operations.
- **Donation Management**: Admins have full control over donations, including viewing, updating, and deleting donations.
- **Category Management**: Manage donation categories with full CRUD operations.
- **Foundation Management**: Manage foundations with full CRUD operations.

## Technologies Used

- **Spring Boot 3+**: For building the backend application.
- **Java 17+**: The programming language used for development.
- **JSP**: For frontend templating.
- **Spring Security**: For authentication and authorization.
- **Docker**: For containerizing the MySQL database.
- **Flyway**: For database migration and inserting necessary data.
- **Spring Data JPA**: For data persistence.
- **Spring Boot Mail**: For email functionalities.
- **MySQL**: The database system used.
- **Hibernate**: ORM framework for database operations.
- **Bootstrap**: For responsive design.
- **JavaScript/jQuery**: For dynamic functionalities.




## Installation

### Prerequisites

- **Docker**: Ensure Docker is installed and running on your machine.
- **MySQL**: Ensure MySQL is installed and accessible via Docker or directly on your machine.


#### Spring Security
Spring Security is configured to protect application resources. Users and roles are defined in the database.

### Clone the Repository

```bash
git clone https://github.com/Jaro95/Charity-JSP.git
cd charity-donation-app
```

## Configure the Application 

Below are the key configuration properties:
```properties
# MySQL Database Configuration
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:${MYSQL_PORT:3302}/${MYSQL_DB_NAME:charity_donation_jsp}?serverTimezone=UTC
spring.datasource.username=${MYSQL_USER:root}
spring.datasource.password=${MYSQL_PASSWORD:saiyan}
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

To disable email verification, open UserServiceImpl.java, set the value on line 44 to true, modify the value on line 45 to "verified" and comment out line 47.

## Run the Application

Open Docker Desktop, run Docker in the application, connect to the created database, and then run the application.

## Usage

- **Access the Application**: Open your browser and navigate to `http://localhost:8080/charity`.

- **Default Users**: After starting the application, default users are:
   - **Super Admin**: 
     - Email: `superAdmin@admin`
     - Password: `admin`
   - **Admin**: 
     - Email: `admin@admin`
     - Password: `admin`
   - **User**: 
     - Email: `user@user`
     - Password: `user`

- **User Registration**: Register a new user by providing your email and password. You will receive a confirmation email.

- **Login**: Log in with your registered email and password.

- **Donation**: Go to the donation section to donate items to selected foundations.

- **Admin Panel**: Access the admin panel to manage users, donations, categories, and foundations.



    