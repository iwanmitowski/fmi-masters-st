-- Потребители
CREATE TABLE IF NOT EXISTS customers(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(256),
    number_of_projects INT DEFAULT 0,
    is_active BOOLEAN DEFAULT True
);
