package com.backend.customerservice.service.serviceinterface;

import com.backend.customerservice.dto.CustomerCreatedResponse;
import com.backend.customerservice.dto.CustomerRequest;
import com.backend.customerservice.dto.CustomerResponse;

public interface ICustomerService {
    CustomerCreatedResponse createCustomer(CustomerRequest customerRequest);
    CustomerResponse getCustomerByExternalId(String externalId);
    CustomerResponse updateCustomer(CustomerRequest customerRequest, String externalId, Integer versionNumber);
}
