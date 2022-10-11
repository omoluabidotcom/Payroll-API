package com.omoluabidotcom.payroll.Controller;

import java.util.List;
import java.util.stream.Collectors;

import com.omoluabidotcom.payroll.entity.Employee;
import com.omoluabidotcom.payroll.error.EmployeeNotFoundException;
import com.omoluabidotcom.payroll.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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
class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    List<Employee> all() {
        return employeeRepository.findAll();
    }

    @PostMapping("/employees")
    Employee newEmployee(@RequestBody Employee newEmployee) {
        return employeeRepository.save(newEmployee);
    }

    // Single item

    @GetMapping("/employees/{id}")
    EntityModel<Employee> one(@PathVariable Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        return EntityModel.of(employee,
                linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).all()).withRel("employee"));
    }

    @PutMapping("/employees/{id}")
    CollectionModel replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

        List<EntityModel<Employee>> employees = employeeRepository.findAll().
                stream().map(employee -> {
                    return EntityModel.of(employee,
                            linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel(),
                            linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));
                }).collect(Collectors.toList());

        return CollectionModel.of(employees,
                linkTo(methodOn(EmployeeController.class).all()).withRel("employee"));
    }

    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
    }

}
