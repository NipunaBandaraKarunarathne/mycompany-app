package com.example.order.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.order.dto.OrderDTO;
import com.example.order.repo.OrderRepo;
import com.example.order.model.Orders;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ModelMapper modelMapper;


    public List<OrderDTO> getOrders() {
     List<Orders>orderList = orderRepo.findAll();
        return modelMapper.map(orderList, new TypeToken<List<OrderDTO>>(){}.getType());
    }

        public OrderDTO saveOrder(OrderDTO orderDTO) {
           orderRepo.save(modelMapper.map(orderDTO, Orders.class));
        return orderDTO;

    }
      public OrderDTO getOrderById(Integer orderId) {
        Orders order = orderRepo.getOrderById(orderId);
        return modelMapper.map(order, OrderDTO.class);
    }

        public OrderDTO updateOrder(OrderDTO orderDTO) {
        orderRepo.save(modelMapper.map(orderDTO, Orders.class));
        return orderDTO;
    }

    public String deleteOrder(Integer orderId) {
        orderRepo.deleteById(orderId);
        return "Order deleted";
    }
 

}
