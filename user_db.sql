CREATE DATABASE IF NOT EXISTS `user_db`;
USE `user_db`;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       id INT NOT NULL AUTO_INCREMENT,
                       first_name VARCHAR(45) NOT NULL,
                       last_name VARCHAR(45) NOT NULL,
                       email VARCHAR(60) NOT NULL,
                       birth_date DATE NOT NULL,
                       address VARCHAR(255),
                       phone_number VARCHAR(45),
                       PRIMARY KEY (id)
);

--
-- Data for table `user`
--

INSERT INTO users (first_name, last_name, email, birth_date, address, phone_number) VALUES
                                                                                        ('Leslie', 'Andrew', 'leslie@gmail.com', '1970-10-05', 'Kyiv, Ukraine', '380730453434'),
                                                                                        ('Emma', 'Fler', 'emma@gmail.com', '1990-01-15', NULL, NULL),
                                                                                        ('Valerie', 'Foust', 'valerie@gmail.com', '2001-11-06', NULL, NULL),
                                                                                        ('Yuri', 'Grey', 'yuri@gmail.com', '1974-03-10', NULL, NULL),
                                                                                        ('John', 'Frost', 'John@gmail.com', '1970-10-05', NULL, NULL);

