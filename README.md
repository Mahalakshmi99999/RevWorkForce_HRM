## RevWorkForce_HRM

- Rev Workforce is a console-based Human Resource Management (HRM) application
- Built using Java and JDBC
- Manages employees, managers, and administrators
- Implements role-based access control
- Uses a normalized relational database

---

## Project Architecture

## Main Layer
- Entry point of the application
- Initializes the system
- Routes execution to controllers based on user role

## Controller Layer
- Handles console-based user interaction
- Displays menus and reads user input
- Invokes appropriate service-layer methods
- Contains no database logic

## Service Layer
- Contains core business logic
- Performs input validation
- Enforces business rules
- Coordinates between multiple DAOs
- Manages application workflows

## DAO Layer
- Handles all database operations using JDBC
- Uses PreparedStatement to prevent SQL injection
- Uses try-with-resources for proper resource management
- Contains DAOs for:
  - Authentication
  - Employee management
  - Leave management
  - Performance reviews
  - Notifications
  - Admin operations

## Model Layer
- Contains POJOs
- Represents database entities
- No business logic or database code
- Core entities include:
  - Employee
  - LeaveRequest
  - LeaveBalance
  - PerformanceReview
  - Goal
  - Notification

## Resource Layer
- Log4j 2 configuration for logging

## Test Layer
- Unit and mock tests
- Uses JUnit 5
- Uses Mockito
- Service layer testing with mocked DAO dependencies
- No real database interaction during tests

---

## Database Design

- Database name: rev_workforce
- Designed using normalization principles
- Enforces referential integrity using foreign keys
- Includes tables for:
  - Departments and designations
  - Employees and login credentials
  - Leave types, leave balances, and leave requests
  - Performance reviews and goals
  - Notifications, holidays, and announcements
- Employee table uses a self-referencing foreign key to represent manager–employee hierarchy

---

## Application Features

## Employee Features
- Login and authentication
- View profile
- Update profile
- Apply for leave
- View leave status
- View leave history
- View leave balance
- View notifications
- View performance reviews

## Manager Features
- View team members
- View pending leave requests
- Approve leave requests
- Reject leave requests
- Review employee performance
- View submitted performance reviews

## Admin Features
- Add new employees
- Assign managers to employees
- View all employees
- Create login credentials for employees
- Audit logging for admin actions

---

## Logging

- Implemented using Log4j 2
- Replaces System.out.println
- Supports INFO, WARN, and ERROR levels
- Logs database operations
- Logs validations and business flow
- Logs errors and exceptions

---

## Testing

- Unit tests written for Service layer
- DAO layer mocked using Mockito
- Reflection-based dependency injection used in tests
- Ensures business logic correctness
- No real database usage during testing

---

## Technologies Used

- Java 21
- JDBC
- MySQL
- Maven
- Log4j 2
- JUnit 5
- Mockito
- IntelliJ IDEA

---

## Project Purpose

- Demonstrates clean layered architecture
- Implements real-world HRM workflows
- Enforces role-based access control
- Uses JDBC with proper database design
- Follows industry-standard logging practices
- Includes unit and mock testing
