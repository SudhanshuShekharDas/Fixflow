CREATE DATABASE IF NOT EXISTS fixflow;
USE fixflow;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    role VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS staff (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(100),
    email VARCHAR(150) UNIQUE
);

CREATE TABLE IF NOT EXISTS service_requests (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    category VARCHAR(100),
    location VARCHAR(255),
    priority VARCHAR(20),
    status VARCHAR(30),
    created_at DATETIME,
    deadline DATETIME,
    resolved_at DATETIME,
    user_id BIGINT,
    staff_id BIGINT,
    CONSTRAINT fk_request_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_request_staff FOREIGN KEY (staff_id) REFERENCES staff(id)
);

INSERT INTO users(name,email,role)
SELECT 'Demo User','user2@fixflow.com','USER'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email='user2@fixflow.com');

INSERT INTO staff(name,department,email)
SELECT 'Vikash Singh','Electrical','vikash@fixflow.com'
WHERE NOT EXISTS (SELECT 1 FROM staff WHERE email='vikash@fixflow.com');
