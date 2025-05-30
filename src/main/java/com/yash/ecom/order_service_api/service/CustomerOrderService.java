package com.yash.ecom.order_service_api.service;

import com.yash.ecom.order_service_api.dto.OrderRequestDto;

public interface CustomerOrderService {
    public void createOrder(OrderRequestDto requestDto);
}
