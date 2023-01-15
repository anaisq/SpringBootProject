CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    role VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    cnp VARCHAR(50) NOT NULL,
    address VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phone_number BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL,

    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS books (
     id BIGINT NOT NULL AUTO_INCREMENT,
     title VARCHAR(255) NOT NULL UNIQUE,
     author VARCHAR(255) NOT NULL,
     part TINYINT DEFAULT 0,
     category VARCHAR(50) NOT NULL,
     number_of_copies INT,
     number_of_available_copies INT,
     price FLOAT,

     PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users_books (
       id BIGINT NOT NULL AUTO_INCREMENT,
       id_user BIGINT NOT NULL,
       id_book BIGINT NOT NULL,
       borrowed_date TIMESTAMP, --DEFAULT CURRENT_TIMESTAMP
       expected_return_date TIMESTAMP, -- DEFAULT DATEADD(day, 60, borrowed_date),
       actual_return_date TIMESTAMP,
       delay INT,
       fee FLOAT,

       PRIMARY KEY (id),
       FOREIGN KEY (id_user) REFERENCES users(id),
       FOREIGN KEY (id_book) REFERENCES books(id)
);


