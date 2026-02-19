package com.example.payroll.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "emp_code", unique = true, nullable = false)
    private String empCode;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String designation;

    @Column(nullable = false)
    private String email;

    @Column
    private String department;

    @Column
    private String phone;

    public Long getId() { return id; }

    public String getEmpCode() { return empCode; }
    public void setEmpCode(String empCode) { this.empCode = empCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
