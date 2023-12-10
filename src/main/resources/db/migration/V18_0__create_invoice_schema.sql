CREATE TABLE invoice (
    id INT PRIMARY KEY AUTO_INCREMENT,
    status TINYINT NOT NULL,
    payment_delay INT NOT NULL,
    customer_id INT NOT NULL,
    client_id INT NOT NULL,
    quotation_id INT,
    deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (client_id) REFERENCES client(id)
);