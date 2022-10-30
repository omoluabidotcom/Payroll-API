package com.omoluabidotcom.payroll.config;

import com.omoluabidotcom.payroll.entity.Employee;
import com.omoluabidotcom.payroll.entity.Order;
import com.omoluabidotcom.payroll.repository.EmployeeRepository;
import com.omoluabidotcom.payroll.repository.OrderRepository;
import com.omoluabidotcom.payroll.utility.Status;
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

    @Autowired
    private OrderRepository orderRepository;

    private static final Logger log = LoggerFactory.getLogger(DummyDatabaseData.class);

    @Bean
    CommandLineRunner commandLineRunner() {

        return args -> {
            employeeRepository.save(new Employee("Goodluck", "Ebele", "Kind"));
            employeeRepository.save(new Employee("Umar", "Yaradua", "Gentle"));

            employeeRepository.findAll().forEach(employee -> log.info("Preloading " + employee));

            orderRepository.save(new Order("MacBook Pro", Status.COMPLETED));
            orderRepository.save(new Order("Samsung", Status.IN_PROGRESS));

            orderRepository.findAll().forEach(order -> log.info("Preloading " + order));

        };
    }

}
