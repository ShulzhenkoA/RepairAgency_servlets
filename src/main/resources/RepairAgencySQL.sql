DROP DATABASE IF EXISTS repair_agency;
CREATE DATABASE repair_agency CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs;

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

INSERT INTO repair_agency.users (first_name, last_name, email, password, language, role) VALUES ('Admin', 'Admin', 'admin@mail.com', -904517469, 'en', 'ADMIN');

DROP DATABASE IF EXISTS test_repair_agency;
CREATE DATABASE test_repair_agency CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs;

CREATE TABLE test_repair_agency.users
(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(64) NOT NULL,
    email VARCHAR(64) NOT NULL,
    password INT NOT NULL,
    language VARCHAR(64) NOT NULL,
    role VARCHAR(32) NOT NULL
);

CREATE TABLE test_repair_agency.cars
(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    brand VARCHAR(32) NOT NULL,
    model VARCHAR(32) NOT NULL,
    year VARCHAR(4) NOT NULL
);

CREATE TABLE test_repair_agency.orders
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
    FOREIGN KEY (customer_id) REFERENCES test_repair_agency.users(id) ON DELETE CASCADE ON UPDATE NO ACTION,
    FOREIGN KEY (car_id) REFERENCES test_repair_agency.cars(id)
);

CREATE TABLE test_repair_agency.reviews
(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    review_date TIMESTAMP NOT NULL,
    review_content VARCHAR(4096) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES test_repair_agency.users(id) ON DELETE CASCADE ON UPDATE NO ACTION
);

DELIMITER //
CREATE PROCEDURE test_repair_agency.add_order(customer_id INT, creation_date TIMESTAMP, car_brand VARCHAR(32),
                                              car_model VARCHAR(32), car_year VARCHAR(32), repair_type VARCHAR(32),
                                              repair_description VARCHAR(4096), status VARCHAR(32))
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
            ROLLBACK;
            RESIGNAL;
        END;
    START TRANSACTION;
    INSERT INTO test_repair_agency.cars(brand, model, year)
    VALUES(car_brand, car_model, car_year);
    INSERT INTO test_repair_agency.orders (customer_id, creation_date, car_id, repair_type, repair_description, status)
    VALUES (customer_id , creation_date, last_insert_id(), repair_type, repair_description, status);
    COMMIT;
END //
DELIMITER ;

INSERT INTO test_repair_agency.users (first_name, last_name, email, password, language, role) VALUES ('Testing', 'Admin', 'testing_admin@mail.com', -904517469, 'en', 'ADMIN');
INSERT INTO test_repair_agency.users (first_name, last_name, email, password, language, role) VALUES ('Testing', 'Customer', 'testing_customer@mail.com', 1076259745, 'en', 'CUSTOMER');
INSERT INTO test_repair_agency.users (first_name, last_name, email, password, language, role) VALUES ('Testing', 'Master', 'testing_master@mail.com', 451058973, 'en', 'MASTER');
INSERT INTO test_repair_agency.users (first_name, last_name, email, password, language, role) VALUES ('Testing', 'Manager', 'testing_manager@mail.com', 937282774, 'en', 'MANAGER');
INSERT INTO test_repair_agency.users (first_name, last_name, email, password, language, role) VALUES ('User for editing', 'Master for editing', 'for_editing_master@mail.com', 451058973, 'en', 'MASTER');
INSERT INTO test_repair_agency.users (first_name, last_name, email, password, language, role) VALUES ('Transaction testing master', 'Transaction testing master', 'transaction_master@mail.com', 451058973, 'en', 'MASTER');

INSERT INTO test_repair_agency.reviews(customer_id, review_date, review_content) VALUES (2, '2020-01-12 09:16:31', 'Testing review');
INSERT INTO test_repair_agency.reviews(customer_id, review_date, review_content) VALUES (2, '2020-01-16 09:16:31', 'Other testing review');
INSERT INTO test_repair_agency.reviews(customer_id, review_date, review_content) VALUES (2, '2020-01-17 09:16:31', 'Simple testing review');

CAll test_repair_agency.add_order (2, now(), 'Some Car', 'Some model', 2007, 'ENGINE_REPAIR', 'Some repair description', 'PENDING');