package com.omoluabidotcom.payroll.Controller;

import java.util.List;
import java.util.stream.Collectors;

import com.omoluabidotcom.payroll.entity.Employee;
import com.omoluabidotcom.payroll.error.EmployeeNotFoundException;
import com.omoluabidotcom.payroll.model.EmployeeModelAssembler;
import com.omoluabidotcom.payroll.repository.EmployeeRepository;
import org.hibernate.EntityMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeModelAssembler employeeModelAssembler;

    @GetMapping("/employees")
    public CollectionModel<EntityModel<Employee>> all() {

        List<EntityModel<Employee>> employees = employeeRepository.findAll().stream()
                .map(employeeModelAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(employees,
                linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
    }

    @PostMapping("/employees")
    ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) {

         EntityModel<Employee> entityModel = employeeModelAssembler.toModel(employeeRepository.save(newEmployee));

         return ResponseEntity
                 .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                 .body(entityModel);
    }

    @GetMapping("/employees/{id}")
    public EntityModel<Employee> one(@PathVariable Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        return employeeModelAssembler.toModel(employee);
    }

    @PutMapping("/employees/{id}")
    ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

        Employee updatedEmployees = employeeRepository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return employeeRepository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return employeeRepository.save(newEmployee);
                });

        EntityModel<Employee> employeeEntityModel = employeeModelAssembler.toModel(updatedEmployees);

        return ResponseEntity
                .created(employeeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(employeeEntityModel);
    }

    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
    }

}
