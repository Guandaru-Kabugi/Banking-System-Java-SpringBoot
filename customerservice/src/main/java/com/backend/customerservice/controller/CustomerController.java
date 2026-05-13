package com.backend.customerservice.controller;

import com.backend.commonsdto.commons.dto.ApiResponse;
import com.backend.customerservice.dto.CustomerCreatedResponse;
import com.backend.customerservice.dto.CustomerRequest;
import com.backend.customerservice.dto.CustomerResponse;
import com.backend.customerservice.service.serviceinterface.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@Tag(name = "Customers", description = "This is a customer microservice")
@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final ICustomerService customerService;

    @Operation(
            summary = "Create a new Customer for the Bank",
            description = "The endpoint is used to create a new customer for the bank."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Created a new customer successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = Void.class))
            )
    })
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createCustomer(
            @RequestBody @Valid CustomerRequest customerRequest) {
        CustomerCreatedResponse response =  customerService.createCustomer(customerRequest);
        URI location = URI.create("/api/v1/customers/create/" + response.getExternalId());
        return ResponseEntity.created(location).body(ApiResponse.success(response));
    }


    @Operation(
            summary = "Get a Customer By External Id",
            description = "The endpoint is used to get a customer for the bank."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Retrieved a customer successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = Void.class))
            )
    })
    @GetMapping("/getByExternalId/{externalId}")
    public ResponseEntity<ApiResponse<?>> getCustomerByExternalId(
            @PathVariable String externalId) {
        CustomerResponse response =  customerService.getCustomerByExternalId(externalId);
        return ResponseEntity.ok().eTag("\"" + response.getVersion() + "\"").body(ApiResponse.success(response));
    }

}
