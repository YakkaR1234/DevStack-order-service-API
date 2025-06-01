package com.yash.ecom.order_service_api.service;

import com.yash.ecom.order_service_api.dto.request.OrderRequestDto;

public interface CustomerOrderService {
    public void createOrder(OrderRequestDto requestDto);
    public  CustomerOrderResponseDto findById(String OrderId);

}
