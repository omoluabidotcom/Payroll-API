package com.omoluabidotcom.payroll.config;

import com.omoluabidotcom.payroll.entity.Employee;
import com.omoluabidotcom.payroll.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DummyDatabaseData {

    @Autowired
    private EmployeeRepository employeeRepository;

    private static final Logger log = LoggerFactory.getLogger(DummyDatabaseData.class);

    @Bean
    CommandLineRunner commandLineRunner(EmployeeRepository repository) {

        return args -> {
            log.info("Preloading " + employeeRepository.save(new Employee("Goodluck", "Ebele", "Kind")));
            log.info("Preloading " + employeeRepository.save(new Employee("Umar", "Yaradua", "Gentle")));
        };
    }
}
