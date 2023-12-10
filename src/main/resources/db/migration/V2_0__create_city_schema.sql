CREATE TABLE city (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    country_id INT NOT NULL,
    FOREIGN KEY (country_id) REFERENCES country (id)
);