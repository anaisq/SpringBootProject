CREATE TABLE IF NOT EXISTS user_details (
    id_user_details BIGINT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    cnp VARCHAR(50) NOT NULL,
    address VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phone_number VARCHAR(50) NOT NULL,

    PRIMARY KEY (id_user_details)
);

CREATE TABLE IF NOT EXISTS book_details (
    id_book_details BIGINT NOT NULL AUTO_INCREMENT,
    part MEDIUMINT,
    category VARCHAR(255) NOT NULL,
    isbn VARCHAR(255) NOT NULL,
    price FLOAT,

     PRIMARY KEY (id_book_details)
);

CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    role VARCHAR(50) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL,
    id_user_details BIGINT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (id_user_details) REFERENCES user_details(id_user_details)
);

CREATE TABLE IF NOT EXISTS books (
     id BIGINT NOT NULL AUTO_INCREMENT,
     title VARCHAR(255) NOT NULL UNIQUE,
     author VARCHAR(255) NOT NULL,
     number_of_copies INT,
     number_of_available_copies INT,
     id_book_details BIGINT NOT NULL,

     PRIMARY KEY (id),
     FOREIGN KEY (id_book_details) REFERENCES book_details(id_book_details)
);

CREATE TABLE IF NOT EXISTS user_books (
     id BIGINT NOT NULL AUTO_INCREMENT,
     id_user BIGINT NOT NULL,
     id_book BIGINT NOT NULL,
     borrowed_date TIMESTAMP, --DEFAULT CURRENT_TIMESTAMP
     delay BOOLEAN,

     PRIMARY KEY (id),
     FOREIGN KEY (id_user) REFERENCES users(id),
     FOREIGN KEY (id_book) REFERENCES books(id)
);


