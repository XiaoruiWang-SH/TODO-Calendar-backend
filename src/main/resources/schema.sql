DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS calendar;

CREATE TABLE users (
    id INT IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE calendar (
    id INT IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    checked BOOLEAN NOT NULL,
    important BOOLEAN NOT NULL,
    createTime DATETIME NOT NULL,
    expireTime DATETIME,
    updateTime DATETIME NOT NULL,
    createDate DATE NOT NULL
);

INSERT INTO users (id, name, email, password) VALUES 
(1, 'John Doe', 'john.doe@example.com', 'password'),
(2, 'Jane Smith', 'jane.smith@example.com', 'password'),
(3, 'Alice Johnson', 'alice.johnson@example.com', 'password');
