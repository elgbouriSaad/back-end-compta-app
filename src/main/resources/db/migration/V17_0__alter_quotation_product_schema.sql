ALTER TABLE quotation_product ADD product_id INT NOT NULL ;
ALTER TABLE quotation_product ADD FOREIGN KEY (product_id) REFERENCES product(id);