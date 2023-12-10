CREATE TABLE product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    label VARCHAR(255) NOT NULL,
    reference VARCHAR(255) NOT NULL,
    price_excl_tax DOUBLE NOT NULL,
    unity VARCHAR(255) NOT NULL,
    qualification VARCHAR(255) NOT NULL,
    tax DOUBLE NOT NULL,
    customer_id INT NOT NULL,
    CONSTRAINT fk_customer2_id FOREIGN KEY (customer_id) REFERENCES customer (id)
);