package com.thejus.brown.springECOM.service;

import com.thejus.brown.springECOM.model.Order;
import com.thejus.brown.springECOM.model.OrderItem;
import com.thejus.brown.springECOM.model.Product;
import com.thejus.brown.springECOM.model.dto.OrderItemRequest;
import com.thejus.brown.springECOM.model.dto.OrderItemResponse;
import com.thejus.brown.springECOM.model.dto.OrderRequest;
import com.thejus.brown.springECOM.model.dto.OrderResponse;
import com.thejus.brown.springECOM.repository.OrderRepo;
import com.thejus.brown.springECOM.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
public class OrderService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private OrderRepo orderRepo;

    public OrderResponse placeOrder(OrderRequest re) {
        Order order=new Order();
        String orderId= "ORD"+UUID.randomUUID().toString().substring(0,8).toUpperCase();
        order.setOrderId(orderId);
        order.setCustomerName(re.customerName());
        order.setEmail(re.email());
        order.setStatus("PLACED");
        order.setOrderDate(LocalDate.now());
        List<OrderItem>orderItems=new ArrayList<>();
        for(OrderItemRequest itemReq : re.items()){
            Product product=productRepo.findById(Math.toIntExact(itemReq.productId()))
                    .orElseThrow(() ->new RuntimeException("Product not found"));
            product.setStockQuantity(product.getStockQuantity() - itemReq.quantity());
            productRepo.save(product);

            OrderItem orderItem=OrderItem.builder()
                    .product(product)
                    .quantity(itemReq.quantity())
                    .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(itemReq.quantity())))
                    .order(order)
                    .build();
            orderItems.add(orderItem);
        }
        order.setItems(orderItems);
        Order saveOrder=orderRepo.save(order);

        List<OrderItemResponse>itemResponses=new ArrayList<>();
        for(OrderItem item:order.getItems()){
            OrderItemResponse orderItemResponse = new OrderItemResponse(
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getTotalPrice()
            );
            itemResponses.add(orderItemResponse);
        }

        OrderResponse orderResponse =new OrderResponse(
                saveOrder.getOrderId(),
                saveOrder.getCustomerName(),
                saveOrder.getEmail(),
                saveOrder.getStatus(),
                saveOrder.getOrderDate(),
                itemResponses
        );
        return orderResponse;
    }

    public List<OrderResponse> getAllOrdersResponses() {
        List<Order>orders=orderRepo.findAll();
        List<OrderResponse>orderResponses=new ArrayList<>();


        for(Order order : orders){

            List<OrderItemResponse>itemResponses=new ArrayList<>();
          for(OrderItem item: order.getItems()){
              OrderItemResponse orderItemResponse =new OrderItemResponse(
                item.getProduct().getName(),
                item.getQuantity(),
                item.getTotalPrice()
              );
              itemResponses.add(orderItemResponse);
          }

            OrderResponse orderResponse=new OrderResponse(
                    order.getOrderId(),
                    order.getCustomerName(),
                    order.getEmail(),
                    order.getStatus(),
                    order.getOrderDate(),
                    itemResponses
            );
            orderResponses.add(orderResponse);
        }
        return orderResponses;
    }
}
