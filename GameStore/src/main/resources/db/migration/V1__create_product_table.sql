CREATE TABLE product (
     id SERIAL PRIMARY KEY,
     name VARCHAR(255) UNIQUE NOT NULL,
     description TEXT,
     condition VARCHAR(50),
     category VARCHAR(50),
     tags TEXT[],
     price DOUBLE PRECISION,
     stock INT NOT NULL,
     image_url VARCHAR(255),
     average_rating DOUBLE PRECISION NOT NULL
);