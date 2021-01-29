CREATE TABLE inventory_users (
    id SERIAL,
    username VARCHAR(50),
    password VARCHAR(64),
    name VARCHAR(30),
    surname VARCHAR(30),
    last_name VARCHAR(30),
    role VARCHAR(20),
    is_enabled BOOLEAN,
    is_deleted BOOLEAN,
    last_login TIMESTAMP
)