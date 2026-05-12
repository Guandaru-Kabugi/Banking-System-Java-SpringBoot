package com.backend.customerservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "customers")
public class Customer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Version
    @Column(name = "version",  nullable = false)
    private Integer version;

    @Column(name="first_name", nullable=false)
    private String firstName;
    @Column(name="last_name",  nullable=false)
    private String lastName;
    @Column(name="email",      nullable=false)
    private String email;
    @Column(name="phone", nullable = false)
    private String phone;
    @Column(name="address",    nullable=false)
    private String address;

    @Column(name="external_id", nullable=false, unique=true)
    private String externalId; // treat as idempotency key for Create

    @Enumerated(EnumType.STRING)
    @Column(name="kyc_status", nullable=false)
    private KycStatus kycStatus;

    @Column(name="active", nullable=false)
    private Boolean active;

    @Column(name="request_fingerprint", nullable=false)
    private String requestFingerprint;

    @Column(name="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
