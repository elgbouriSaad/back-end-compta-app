CREATE TABLE client (
    id INT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(255) NOT NULL,
    rc INT NOT NULL,
    email VARCHAR(255) NOT NULL,
    address_id INT NOT NULL,
    mobile_phone VARCHAR(20) NOT NULL,
    phone VARCHAR(20),
    fax VARCHAR(20),
    deleted BOOLEAN DEFAULT FALSE,
    customer_id INT NOT NULL,
    CONSTRAINT fk_address3_id FOREIGN KEY (address_id) REFERENCES address (id),
    CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES customer (id)
);