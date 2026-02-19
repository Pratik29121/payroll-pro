package com.example.payroll.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "salary")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    private double basic;
    private double hra;
    private double bonus;
    private double pf;
    private String month;
    private int year;

    public Long getId() { return id; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    public double getBasic() { return basic; }
    public void setBasic(double basic) { this.basic = basic; }

    public double getHra() { return hra; }
    public void setHra(double hra) { this.hra = hra; }

    public double getBonus() { return bonus; }
    public void setBonus(double bonus) { this.bonus = bonus; }

    public double getPf() { return pf; }
    public void setPf(double pf) { this.pf = pf; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public double getNetSalary() {
        return basic + hra + bonus - pf;
    }

    public double getGrossSalary() {
        return basic + hra + bonus;
    }
}
