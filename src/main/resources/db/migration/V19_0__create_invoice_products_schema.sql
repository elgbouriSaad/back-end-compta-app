CREATE TABLE invoice_product (
	id INT PRIMARY KEY AUTO_INCREMENT,
	invoice_id INT NOT NULL,
	product_id INT NOT NULL,
	label VARCHAR(255) NOT NULL,
    reference VARCHAR(255) NOT NULL,
    price_excl_tax DOUBLE NOT NULL,
    unity VARCHAR(255) NOT NULL,
    qualification VARCHAR(255) NOT NULL,
    tax DOUBLE NOT NULL,
    quantity INT NOT NULL,
    deleted BOOLEAN DEFAULT FALSE,
	FOREIGN KEY (invoice_id) REFERENCES invoice (id),
	FOREIGN KEY (product_id) REFERENCES product (id)
);