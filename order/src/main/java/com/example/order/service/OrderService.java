package com.example.order.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.order.common.ErrorOrderResponse;
import com.example.order.common.SuccessOrderResponse;
import com.example.order.common.orderResponse;
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

    public OrderService(WebClient.Builder webClientBuilder, ModelMapper modelMapper, OrderRepo orderRepo) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/api/v1").build();
        this.modelMapper = modelMapper;
        this.orderRepo = orderRepo;
    }

    public List<OrderDTO> getOrders() {
        List<Orders> orderList = orderRepo.findAll();
        return modelMapper.map(orderList, new TypeToken<List<OrderDTO>>() {
        }.getType());
    }

    public orderResponse saveOrder(OrderDTO orderDTO) {

        Integer itemId = orderDTO.getItemId();

        InventoryDTO inventoryResponse;

        try {
            inventoryResponse = webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/inventory/{itemId}").build(itemId))
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
            return new SuccessOrderResponse(orderDTO);


        }
        else{
            return new ErrorOrderResponse("Item is out of stock: " + itemId);
        }

       
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

