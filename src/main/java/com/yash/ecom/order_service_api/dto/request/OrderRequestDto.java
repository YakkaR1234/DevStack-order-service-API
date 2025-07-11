package com.yash.ecom.order_service_api.dto.request;

import com.yash.ecom.order_service_api.dto.request.OrderDetailRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {

    private Date orderDate;
    private double totalAmount;
    private String userId;
    private ArrayList<OrderDetailRequestDto> orderDetails;

}
