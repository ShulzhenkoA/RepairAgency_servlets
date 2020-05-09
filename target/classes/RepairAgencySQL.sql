DROP DATABASE IF EXISTS repair_agency;
CREATE DATABASE repair_agency CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

CREATE TABLE repair_agency.users
(
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
first_name VARCHAR(64) NOT NULL,
last_name VARCHAR(64) NOT NULL,
email VARCHAR(64) NOT NULL,
password INT NOT NULL,
language VARCHAR(64) NOT NULL,
role VARCHAR(32) NOT NULL
);

CREATE TABLE repair_agency.cars
(
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
brand VARCHAR(32) NOT NULL,
model VARCHAR(32) NOT NULL,
year VARCHAR(4) NOT NULL
);

CREATE TABLE repair_agency.orders
(
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
customer_id INT NOT NULL,
creation_date TIMESTAMP NOT NULL,
car_id int NOT NULL,
repair_type VARCHAR(32) NOT NULL,
repair_description VARCHAR(4096) NOT NULL,
price DOUBLE,
master_id INT,
repair_completion_date TIMESTAMP,
status VARCHAR(32) NOT NULL,
manager_comment VARCHAR(4096),
FOREIGN KEY (customer_id) REFERENCES repair_agency.users(id) ON DELETE CASCADE ON UPDATE NO ACTION,
FOREIGN KEY (car_id) REFERENCES repair_agency.cars(id)
);

CREATE TABLE repair_agency.reviews
(
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
customer_id INT NOT NULL,
review_date TIMESTAMP NOT NULL,
review_content VARCHAR(4096) NOT NULL,
FOREIGN KEY (customer_id) REFERENCES repair_agency.users(id) ON DELETE CASCADE ON UPDATE NO ACTION
);

DELIMITER //
    CREATE PROCEDURE repair_agency.add_order(customer_id INT, creation_date TIMESTAMP, car_brand VARCHAR(32),
                                            car_model VARCHAR(32), car_year VARCHAR(32), repair_type VARCHAR(32),
                                            repair_description VARCHAR(4096), status VARCHAR(32))
    BEGIN
        DECLARE EXIT HANDLER FOR SQLEXCEPTION
            BEGIN
               ROLLBACK;
               RESIGNAL;
            END;
        START TRANSACTION;
            INSERT INTO cars(brand, model, year)
            VALUES(car_brand, car_model, car_year);
            INSERT INTO orders (customer_id, creation_date, car_id, repair_type, repair_description, status)
            VALUES (customer_id , creation_date, last_insert_id(), repair_type, repair_description, status);
        COMMIT;
    END //
DELIMITER ;