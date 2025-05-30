package com.yash.ecom.order_service_api.repo;

import com.yash.ecom.order_service_api.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.xml.crypto.Data;
import java.sql.Date;

public interface CustomerOrderRepo extends JpaRepository<CustomerOrder,String> {


}
