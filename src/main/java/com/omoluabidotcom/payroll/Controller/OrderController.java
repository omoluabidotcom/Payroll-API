package com.omoluabidotcom.payroll.Controller;

import com.omoluabidotcom.payroll.model.OrderModelAssembler;
import com.omoluabidotcom.payroll.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private OrderRepository orderRepository;

    private OrderModelAssembler orderModelAssembler;

    @Autowired
    public OrderController(OrderRepository orderRepository,
                           OrderModelAssembler orderModelAssembler)
    {
        this.orderRepository = orderRepository;
        this.orderModelAssembler = orderModelAssembler;
    }

}
