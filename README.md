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


## 🚀 How to Run

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8+

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
