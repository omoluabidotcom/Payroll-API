package com.omoluabidotcom.payroll.model;

import com.omoluabidotcom.payroll.Controller.EmployeeController;
import com.omoluabidotcom.payroll.entity.Order;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {

    @Override
    public EntityModel<Order> toModel(Order order) {
        return EntityModel.of(order,
                linkTo(methodOn(EmployeeController.class).one(order.getId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).all()).withRel("orders"));
    }
}
