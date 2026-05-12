CREATE SCHEMA IF NOT EXISTS banking_system;

CREATE TABLE banking_system.customers (
                                        id BIGSERIAL PRIMARY KEY,
                                        version INTEGER NOT NULL,
                                        first_name VARCHAR(50),
                                        last_name VARCHAR(50),
                                        email VARCHAR(255) NOT NULL UNIQUE,
                                        phone VARCHAR(100) NOT NULL,
                                        address VARCHAR(100) NOT NULL,
                                        external_id VARCHAR(200) NOT NULL UNIQUE,
                                        kyc_status VARCHAR(100),
                                        active BOOLEAN NOT NULL,
                                        request_fingerprint VARCHAR(450),
                                        created_at TIMESTAMP,
                                        updated_at TIMESTAMP
);