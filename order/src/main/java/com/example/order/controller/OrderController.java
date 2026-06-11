package com.example.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.order.dto.OrderDTO;
import com.example.order.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getorders")
     public List<OrderDTO> getOrders() {
        return orderService.getOrders();
    }

      @PostMapping("/addorder")
    public OrderDTO addOrder(@RequestBody OrderDTO orderDTO) {

        return orderService.saveOrder(orderDTO);
    }

        @GetMapping("/order/{orderId}")
    public OrderDTO getOrderById(@PathVariable Integer orderId) {
        return orderService.getOrderById(orderId);
    }

       @PutMapping("/updateorder")
    public OrderDTO updateProduct(@RequestBody OrderDTO productDTO) {
        return orderService.updateOrder(productDTO);
    }

    @DeleteMapping("/deleteorder/{orderId}")
    public String deleteProduct(@PathVariable Integer orderId) {
        return orderService.deleteOrder(orderId);
    }
    

}
