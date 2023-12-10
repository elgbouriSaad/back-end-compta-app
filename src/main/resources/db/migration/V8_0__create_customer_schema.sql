CREATE TABLE customer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(255) NOT NULL,
    rc INT NOT NULL,
    email VARCHAR(255) NOT NULL,
    address_id INT NOT NULL,
    mobile_phone VARCHAR(20) NOT NULL,
    phone VARCHAR(20),
    fax VARCHAR(20),
    deleted BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_address2_id FOREIGN KEY (address_id) REFERENCES address (id)
);
