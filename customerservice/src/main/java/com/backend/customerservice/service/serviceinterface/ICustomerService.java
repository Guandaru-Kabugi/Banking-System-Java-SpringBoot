package com.backend.customerservice.service.serviceinterface;

import com.backend.customerservice.dto.CustomerCreatedResponse;
import com.backend.customerservice.dto.CustomerRequest;
import com.backend.customerservice.model.Customer;

public interface ICustomerService {
    CustomerCreatedResponse createCustomer(CustomerRequest customerRequest);
}
