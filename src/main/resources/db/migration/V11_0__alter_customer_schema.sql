ALTER TABLE customer ADD accountant_id INT NOT NULL ;
ALTER TABLE customer ADD FOREIGN KEY (accountant_id) REFERENCES accountant(id);