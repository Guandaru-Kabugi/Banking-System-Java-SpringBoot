package com.backend.customerservice.mapper;

import com.backend.customerservice.dto.CustomerRequest;
import com.backend.customerservice.dto.CustomerResponse;
import com.backend.customerservice.model.Customer;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toEntity(CustomerRequest request);

    CustomerResponse toResponse(Customer customer);

//    CustomerCreatedResponse toCreateResponse(Customer customer);

//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    void updateCustomerFromRequest(UpdateCustomerRequest request, @MappingTarget Customer customer);

}
