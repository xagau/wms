CREATE TABLE billing (
                         id INT NOT NULL AUTO_INCREMENT,
                         account VARCHAR(255) NOT NULL,
                         frozen BOOLEAN NOT NULL,
                         PRIMARY KEY (id)
);