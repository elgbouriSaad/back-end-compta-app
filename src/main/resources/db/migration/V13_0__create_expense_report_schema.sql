CREATE TABLE expense_report (
    id INT PRIMARY KEY AUTO_INCREMENT,
    status VARCHAR(255) NOT NULL,
    label VARCHAR(255) NOT NULL,
    price_excl_tax DOUBLE NOT NULL,
    qualification VARCHAR(255) NOT NULL,
    tax DOUBLE NOT NULL,
    customer_id INT NOT NULL,
    deleted BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_customer4_id FOREIGN KEY (customer_id) REFERENCES customer (id)
);