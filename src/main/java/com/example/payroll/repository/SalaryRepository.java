package com.example.payroll.repository;

import com.example.payroll.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
    List<Salary> findByEmployeeId(Long employeeId);
    List<Salary> findByMonthAndYear(String month, int year);
}
