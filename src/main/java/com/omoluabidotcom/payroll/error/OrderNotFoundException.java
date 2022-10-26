package com.omoluabidotcom.payroll.error;

public class OrderNotFoundException extends  RuntimeException{

    public OrderNotFoundException(Long id) {
        super("Order with the id "+id+" does not exist");
    }
}
