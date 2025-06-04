package com.yash.ecom.order_service_api.service.impl;

import com.yash.ecom.order_service_api.dto.request.OrderRequestDto;
import com.yash.ecom.order_service_api.dto.request.OrderDetailRequestDto;
import com.yash.ecom.order_service_api.dto.response.CustomerOrderResponseDto;
import com.yash.ecom.order_service_api.dto.response.OrderDetailResponseDto;
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

    @Override
    public CustomerOrderResponseDto findOrderId(String orderId) {
        CustomerOrder customerOrder = customerOrderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException(String.format("Order not found: %s", orderId)));
        return toCustomerOrderResponseDto(customerOrder);
    }

    @Override
    public  void deleteById(String order){
        CustomerOrder customerOrder=
                customerOrderRepo.findById(orderId).orElseThrow(()->new RuntimeException(String.format("Order not found",orderId)))
                customerOrderRepo.delete(customerOrder);
    }

    private CustomerOrderResponseDto toCustomerOrderResponseDto(CustomerOrder customerOrder) {
        if (customerOrder == null) {
            return null;
        }

        return CustomerOrderResponseDto.builder()
                .orderId(customerOrder.getOrderId())
                .orderDate(customerOrder.getOrderDate())
                .userId(customerOrder.getUserId())
                .totalAmount(customerOrder.getTotalAmount())
                .orderDetails(
                        customerOrder.getProducts().stream()
                                .map(this::toOrderDetailResponseDto)
                                .collect(Collectors.toList())
                )
                .remark(customerOrder.getRemark())
                .status(customerOrder.getOrderStatus().getStatus())
                .build();
    }

    private OrderDetailResponseDto toOrderDetailResponseDto(OrderDetail orderDetail) {
        if (orderDetail == null) {
            return null;
        }

        return OrderDetailResponseDto.builder()
                .productId(orderDetail.getProductId())
                .detailId(orderDetail.getDetailId())
                .discount(orderDetail.getDiscount())
                .qty(orderDetail.getQty())
                .unitPrice(orderDetail.getUnitPrice())
                .build();
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
                .productId(detailDto.getProductId())
                .customerOrder(order)
                .build();
    }
}
