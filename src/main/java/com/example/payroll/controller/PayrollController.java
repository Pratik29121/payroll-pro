package com.example.payroll.controller;

import com.example.payroll.entity.Employee;
import com.example.payroll.entity.Salary;
import com.example.payroll.repository.EmployeeRepository;
import com.example.payroll.repository.SalaryRepository;
import com.example.payroll.service.PdfService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payroll")
@CrossOrigin(origins = "*")
public class PayrollController {

    private final EmployeeRepository employeeRepository;
    private final SalaryRepository salaryRepository;
    private final PdfService pdfService;

    public PayrollController(EmployeeRepository employeeRepository,
                             SalaryRepository salaryRepository,
                             PdfService pdfService) {
        this.employeeRepository = employeeRepository;
        this.salaryRepository = salaryRepository;
        this.pdfService = pdfService;
    }

    @GetMapping("/salaries")
    public List<Salary> getAllSalaries() {
        return salaryRepository.findAll();
    }

    @GetMapping("/salaries/employee/{empId}")
    public List<Salary> getSalariesByEmployee(@PathVariable Long empId) {
        return salaryRepository.findByEmployeeId(empId);
    }

    @PostMapping("/salary/{empId}")
    public ResponseEntity<Salary> createSalary(@PathVariable Long empId,
                                               @RequestBody Salary salary) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        salary.setEmployee(employee);
        return ResponseEntity.ok(salaryRepository.save(salary));
    }

    @PutMapping("/salary/{salaryId}")
    public ResponseEntity<Salary> updateSalary(@PathVariable Long salaryId,
                                               @RequestBody Salary updated) {
        return salaryRepository.findById(salaryId).map(sal -> {
            sal.setBasic(updated.getBasic());
            sal.setHra(updated.getHra());
            sal.setBonus(updated.getBonus());
            sal.setPf(updated.getPf());
            sal.setMonth(updated.getMonth());
            sal.setYear(updated.getYear());
            return ResponseEntity.ok(salaryRepository.save(sal));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/salary/{salaryId}")
    public ResponseEntity<Void> deleteSalary(@PathVariable Long salaryId) {
        if (!salaryRepository.existsById(salaryId)) return ResponseEntity.notFound().build();
        salaryRepository.deleteById(salaryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/salary-slip/{salaryId}")
    public ResponseEntity<byte[]> downloadSlip(@PathVariable Long salaryId) {
        Salary salary = salaryRepository.findById(salaryId)
                .orElseThrow(() -> new RuntimeException("Salary not found"));
        byte[] pdf = pdfService.generateSlip(salary);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=salary-slip.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
