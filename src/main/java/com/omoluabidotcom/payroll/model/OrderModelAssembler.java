package com.omoluabidotcom.payroll.model;

import com.omoluabidotcom.payroll.Controller.EmployeeController;
import com.omoluabidotcom.payroll.Controller.OrderController;
import com.omoluabidotcom.payroll.entity.Order;
import com.omoluabidotcom.payroll.utility.Status;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {

    @Override
    public EntityModel<Order> toModel(Order order) {
        EntityModel<Order> orderModel = EntityModel.of(order,
                linkTo(methodOn(OrderController.class).one(order.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).all()).withRel("orders"));

        if(order.getStatus() == Status.IN_PROGRESS) {
            orderModel.add(linkTo(methodOn(OrderController.class).cancelOrder(order.getId())).withRel("cancel"));

            orderModel.add(linkTo(methodOn(OrderController.class).completeOrder(order.getId())).withRel("comlete"));
        }

        return orderModel;
    }
}
