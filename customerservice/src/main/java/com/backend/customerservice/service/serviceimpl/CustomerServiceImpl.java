package com.backend.customerservice.service.serviceimpl;

import com.backend.commonsdto.commons.exception.ConflictException;
import com.backend.customerservice.dto.CustomerCreatedResponse;
import com.backend.customerservice.dto.CustomerRequest;
import com.backend.customerservice.mapper.CustomerMapper;
import com.backend.customerservice.model.Customer;
import com.backend.customerservice.model.KycStatus;
import com.backend.customerservice.repository.CustomerRepository;
import com.backend.customerservice.service.serviceinterface.ICustomerService;
import com.backend.customerservice.util.Fingerprints;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;


    @Override
    public CustomerCreatedResponse createCustomer(CustomerRequest customerRequest) {

        String externalId = customerRequest.getExternalId();
        String email = customerRequest.getEmail();

        String fingerPrint = Fingerprints.customerCreate(
                customerRequest.getFirstName(),
                customerRequest.getLastName(),
                customerRequest.getEmail(),
                customerRequest.getPhone(),
                customerRequest.getAddress()
        );
        //check if externalId already exists, we return the same record
        Optional<Customer> findByTheirExternalId = customerRepository.findByExternalId(externalId);
        if (findByTheirExternalId.isPresent()) {
            Customer customerById = findByTheirExternalId.get();
            if (fingerPrint.equals(customerById.getRequestFingerprint())){
                return customerMapper.toCreateResponse(customerById);
            }
            throw new ConflictException("There is an already existing external id with different data set");
        }
        //check if email already exists, we return the same record
        Optional<Customer> findByTheirEmail = customerRepository.findByEmail(email);
        if (findByTheirEmail.isPresent()) {
            Customer customerByEmail = findByTheirEmail.get();
            if (fingerPrint.equals(customerByEmail.getRequestFingerprint())){
                return customerMapper.toCreateResponse(customerByEmail);
            }
            throw new ConflictException("There is an already existing email with different data set");
        }
        //save record to db
        Customer customerEntity = customerMapper.toEntity(customerRequest);
        customerEntity.setActive(false);
        customerEntity.setKycStatus(KycStatus.PENDING);
        customerEntity.setRequestFingerprint(fingerPrint);
        Customer customerSaved = customerRepository.save(customerEntity);

        //return the response
        return customerMapper.toCreateResponse(customerSaved);
    }

    private boolean exists(String externalId) {
        return customerRepository.findByExternalId(externalId).isPresent();

    }

    private boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }
}
