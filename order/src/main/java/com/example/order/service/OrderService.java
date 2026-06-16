package com.example.order.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.order.dto.OrderDTO;
import com.example.order.repo.OrderRepo;
import com.example.order.model.Orders;

import jakarta.transaction.Transactional;

import com.example.inventory.dto.InventoryDTO;

@Service
@Transactional
public class OrderService {
    private final WebClient webClient;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ModelMapper modelMapper;

    public OrderService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<OrderDTO> getOrders() {
        List<Orders> orderList = orderRepo.findAll();
        return modelMapper.map(orderList, new TypeToken<List<OrderDTO>>() {
        }.getType());
    }

    public OrderDTO saveOrder(OrderDTO orderDTO) {

        Integer itemId = orderDTO.getItemId();

        InventoryDTO inventoryResponse;

        try {
            inventoryResponse = webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("http://localhost:8080/api/v1/inventory/{itemId}").build(itemId))
                    .retrieve()
                    .bodyToMono(InventoryDTO.class)
                    .block();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to fetch inventory data for item ID: " + itemId, e);
        }

        assert inventoryResponse != null;

        if(inventoryResponse.getQuantity()>0){

            orderRepo.save(modelMapper.map(orderDTO, Orders.class));
            return orderDTO;


        }
        else{
            throw new RuntimeException("Item is out of stock: " + itemId);
        }

        // if (inventoryResponse == null) {
        //     throw new RuntimeException("Inventory not found for item ID: " + itemId);
        // }

        // if (inventoryResponse.getQuantity() <= 0) {
        //     throw new RuntimeException("Item is out of stock: " + itemId);
        // }

        // save order
       // Orders order = modelMapper.map(orderDTO, Orders.class);
      //  Orders savedOrder = orderRepo.save(order);

       // return modelMapper.map(savedOrder, OrderDTO.class);
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
