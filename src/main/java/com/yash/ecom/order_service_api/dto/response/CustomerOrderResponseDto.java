package com.yash.ecom.order_service_api.dto.response;

import com.yash.ecom.order_service_api.dto.request.OrderDetailRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import lombok.Builder;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerOrderResponseDto {

    private String orderId;
    private Date orderDate;
    private double totalAmount;
    private String userId;
    private String remark;
    private String status;
    private List<CustomerOrderDetailResponseDto> orderDetails;

}
