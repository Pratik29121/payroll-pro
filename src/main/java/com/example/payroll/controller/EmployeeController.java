package com.example.payroll.controller;

import com.example.payroll.entity.Employee;
import com.example.payroll.repository.EmployeeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Employee> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Employee create(@RequestBody Employee employee) {
        return repository.save(employee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee updated) {
        return repository.findById(id).map(emp -> {
            emp.setName(updated.getName());
            emp.setEmpCode(updated.getEmpCode());
            emp.setDesignation(updated.getDesignation());
            emp.setEmail(updated.getEmail());
            emp.setDepartment(updated.getDepartment());
            emp.setPhone(updated.getPhone());
            return ResponseEntity.ok(repository.save(emp));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
