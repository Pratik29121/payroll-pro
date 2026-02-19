# 💼 PayrollPro - Payroll Management System

A full-stack payroll management system built with **Spring Boot** (backend) and **HTML/CSS/JavaScript** (frontend).

---

## ✅ Features

### Frontend
- 📊 **Dashboard** — Stats overview: total employees, salary, deductions
- 👥 **Employee Management** — Add, Edit, Delete employees with search/filter
- 💰 **Salary Management** — Add, Edit, Delete salary records; auto net-salary calculation
- 🖨️ **Salary Slip** — Preview salary slip in browser, print or download as PDF

### Backend (Spring Boot REST API)
- `GET/POST/PUT/DELETE /api/employees` — Full employee CRUD
- `GET /api/payroll/salaries` — List all salaries
- `POST /api/payroll/salary/{empId}` — Add salary for employee
- `PUT /api/payroll/salary/{id}` — Edit salary
- `DELETE /api/payroll/salary/{id}` — Delete salary
- `GET /api/payroll/salary-slip/{id}` — Download PDF salary slip

---

## 🛠️ Fixes Applied

1. **Duplicate PdfService** removed (`src/main/java/service/PdfService.java` was a stray file)
2. **Salary entity** changed from `@OneToOne` to `@ManyToOne` (one employee can have multiple monthly salaries)
3. **Missing CRUD** endpoints added for both employees and salaries
4. **`@CrossOrigin`** added to all controllers
5. **API paths** unified to `/api/employees` and `/api/payroll/...`
6. **Month & Year** fields added to Salary entity
7. **Employee** entity extended with `department` and `phone` fields
8. **PdfService** redesigned with professional layout, tables, colors
9. **Frontend** completely built from scratch — demo mode if backend unavailable

---

## 🚀 How to Run

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8+

### Database Setup
```sql
CREATE DATABASE payroll_db;
CREATE USER 'payroll_user'@'localhost' IDENTIFIED BY 'payroll123';
GRANT ALL ON payroll_db.* TO 'payroll_user'@'localhost';
```
> Or update `application.properties` with your MySQL credentials.

### Run
```bash
mvn clean install
mvn spring-boot:run
```

Open browser: **http://localhost:8080**

---

## 📁 Project Structure
```
src/
├── main/
│   ├── java/com/example/payroll/
│   │   ├── PayrollApplication.java
│   │   ├── controller/
│   │   │   ├── EmployeeController.java
│   │   │   └── PayrollController.java
│   │   ├── entity/
│   │   │   ├── Employee.java
│   │   │   └── Salary.java
│   │   ├── repository/
│   │   │   ├── EmployeeRepository.java
│   │   │   └── SalaryRepository.java
│   │   └── service/
│   │       └── PdfService.java
│   └── resources/
│       ├── application.properties
│       └── static/
│           └── index.html   ← Frontend SPA
```
