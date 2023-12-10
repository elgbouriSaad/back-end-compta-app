CREATE TABLE quotation_product (
	id INT PRIMARY KEY AUTO_INCREMENT,
	quotation_id INT NOT NULL,
	label VARCHAR(255) NOT NULL,
    reference VARCHAR(255) NOT NULL,
    price_excl_tax DOUBLE NOT NULL,
    unity VARCHAR(255) NOT NULL,
    qualification VARCHAR(255) NOT NULL,
    tax DOUBLE NOT NULL,
    quantity INT NOT NULL,
    deleted BOOLEAN DEFAULT FALSE,
	CONSTRAINT fk_quotation_id FOREIGN KEY (quotation_id) REFERENCES quotation (id)
);