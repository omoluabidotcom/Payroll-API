package com.omoluabidotcom.payroll.error;

public class EmployeeNotFoundException extends RuntimeException{

    public EmployeeNotFoundException(Long id) {

        super("Employee with the id " + id + " does not exist");
    }
}
