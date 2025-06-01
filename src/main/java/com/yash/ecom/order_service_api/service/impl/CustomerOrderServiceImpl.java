package com.yash.ecom.order_service_api.service.impl;

import com.yash.ecom.order_service_api.dto.request.OrderRequestDto;
import com.yash.ecom.order_service_api.dto.request.OrderDetailRequestDto;
import com.yash.ecom.order_service_api.entity.CustomerOrder;
import com.yash.ecom.order_service_api.entity.OrderDetail;
import com.yash.ecom.order_service_api.entity.OrderStatus;
import com.yash.ecom.order_service_api.repo.CustomerOrderRepo;
import com.yash.ecom.order_service_api.repo.OrderStatusRepo;
import com.yash.ecom.order_service_api.service.CustomerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerOrderServiceImpl implements CustomerOrderService {

    private final CustomerOrderRepo customerOrderRepo;
    private final OrderStatusRepo orderStatusRepo;

    @Override
    public void createOrder(OrderRequestDto requestDto) {

        OrderStatus orderStatus = orderStatusRepo.findByStatus("PENDING")
                .orElseThrow(() -> new RuntimeException("Order status not found"));

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setOrderId(UUID.randomUUID().toString());
        customerOrder.setOrderDate(requestDto.getOrderDate());
        customerOrder.setRemark(" ");
        customerOrder.setTotalAmount(requestDto.getTotalAmount());
        customerOrder.setUserId(requestDto.getUserId());
        customerOrder.setOrderStatus(orderStatus);

        customerOrder.setProducts(
                requestDto.getOrderDetails()
                        .stream()
                        .map(detailDto -> createOrderDetail(detailDto, customerOrder))
                        .collect(Collectors.toSet())
        );

        customerOrderRepo.save(customerOrder);
    }

    private OrderDetail createOrderDetail(OrderDetailRequestDto detailDto, CustomerOrder order) {
        if (detailDto == null) {
            return null;
        }

        return OrderDetail.builder()
                .detailId(UUID.randomUUID().toString())
                .unitPrice(detailDto.getUnitPrice())
                .discount(detailDto.getDiscount())
                .qty(detailDto.getQty())
                .customerOrder(order)
                .build();
    }
}
