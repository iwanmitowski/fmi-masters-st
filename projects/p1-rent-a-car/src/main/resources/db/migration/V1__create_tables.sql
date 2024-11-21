CREATE TABLE cities (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

INSERT INTO cities (name) VALUES
('Пловдив'),
('София'),
('Варна'),
('Бургас');

CREATE TABLE clients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL UNIQUE,
    age INT NOT NULL,
    accident_history BOOLEAN NOT NULL
);

INSERT INTO clients (name, address, phone, age, accident_history)
VALUES
    ('Иван Иванов', 'София', '+359885123456', 35, FALSE),
    ('Мария Иванова', 'Пловдив', '+359878654321', 29, TRUE),
    ('Георги Георгиев', 'Бургас', '+359897112233', 42, FALSE),
    ('Никол Николова', 'Пловдив', '+359899998877', 31, TRUE),
    ('Калоян Калоянов', 'Пловдив', '+359887654321', 27, FALSE);

CREATE TABLE cars (
    id INT AUTO_INCREMENT PRIMARY KEY,
    make VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    manufactured_year INT NOT NULL,
    availability BOOLEAN NOT NULL DEFAULT TRUE,
    price_per_day DECIMAL(10,2) NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    city_id INT NOT NULL,
    FOREIGN KEY (city_id) REFERENCES cities(id)
);

INSERT INTO cars (make, model, manufactured_year, availability, price_per_day, city_id)
VALUES
    ('Toyota', 'Corolla', 2020, TRUE, 45.00, 1),
    ('VW', 'Golf', 2019, TRUE, 40.00, 2),
    ('BMW', 'X5', 2021, TRUE, 90.00, 3),
    ('Ford', 'Fiesta', 2018, TRUE, 35.00, 4),
    ('Audi', 'A4', 2021, TRUE, 85.00, 1);

CREATE TABLE status (
    id INT AUTO_INCREMENT PRIMARY KEY,
    status_name VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO status (status_name)
VALUES
    ('Изчакваща'),
    ('Приета'),
    ('Отказана');

CREATE TABLE offers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    client_id INT NOT NULL,
    car_id INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    status_id INT NOT NULL,
    offer_date DATE NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (client_id) REFERENCES clients(id),
    FOREIGN KEY (car_id) REFERENCES cars(id),
    FOREIGN KEY (status_id) REFERENCES status(id)
);

-- DROP TABLE IF EXISTS offers;
-- DROP TABLE IF EXISTS status;
-- DROP TABLE IF EXISTS cars;
-- DROP TABLE IF EXISTS clients;
-- DROP TABLE IF EXISTS cities;
