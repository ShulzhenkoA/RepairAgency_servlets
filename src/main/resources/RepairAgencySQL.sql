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

CREATE TABLE repair_agency.orders
(
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
customer_id INT NOT NULL,
order_date TIMESTAMP NOT NULL,
order_content VARCHAR(4096) NOT NULL,
order_price DOUBLE,
master_id INT,
repair_completion_date TIMESTAMP,
order_status VARCHAR(32) NOT NULL,
order_manager_info VARCHAR(4096),
FOREIGN KEY (customer_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE NO ACTION
);

CREATE TABLE repair_agency.orders
(
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
customer_id INT NOT NULL,
order_date TIMESTAMP NOT NULL,
order_content VARCHAR(4096) NOT NULL,
order_price DOUBLE,
master_id INT,
repair_completion_date TIMESTAMP,
order_status VARCHAR(32) NOT NULL,
order_manager_info VARCHAR(4096),
FOREIGN KEY (customer_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE NO ACTION
);





