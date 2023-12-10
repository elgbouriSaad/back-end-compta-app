CREATE TABLE address (
    id INT AUTO_INCREMENT PRIMARY KEY,
    primary_address VARCHAR(255) NOT NULL,
    secondary_address VARCHAR(255),
    postal_code INT NOT NULL,
    city_id INT NOT NULL,
    FOREIGN KEY (city_id) REFERENCES city (id)
);