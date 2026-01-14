package com.thejus.brown.springECOM.model.dto;

public record OrderItemRequest(
        Long productId,
        Integer quantity
) {
}
