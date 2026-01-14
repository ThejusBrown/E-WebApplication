package com.thejus.brown.springECOM.controller;

import com.thejus.brown.springECOM.model.dto.OrderItemRequest;
import com.thejus.brown.springECOM.model.dto.OrderItemResponse;
import com.thejus.brown.springECOM.model.dto.OrderRequest;
import com.thejus.brown.springECOM.model.dto.OrderResponse;
import com.thejus.brown.springECOM.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderservice;
    @PostMapping("/orders/place")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest){
       OrderResponse orderResponse = orderservice.placeOrder(orderRequest);
       return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>>getAllOrders(){
        List<OrderResponse>responses=orderservice.getAllOrdersResponses();
        return new ResponseEntity<>(responses,HttpStatus.OK);
    }
}
