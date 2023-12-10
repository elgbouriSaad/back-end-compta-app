CREATE TABLE quotation (
    id INT PRIMARY KEY AUTO_INCREMENT,
    status VARCHAR(255) NOT NULL,
    validation_delay INT NOT NULL,
    customer_id INT NOT NULL,
	client_id INT NOT NULL,
	deleted BOOLEAN DEFAULT FALSE,
	CONSTRAINT fk_customer3_id FOREIGN KEY (customer_id) REFERENCES customer (id),
	CONSTRAINT fk_client_id FOREIGN KEY (client_id) REFERENCES client (id)
);