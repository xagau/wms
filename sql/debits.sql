CREATE TABLE debits (
                        id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        owner VARCHAR (255) NOT NULL,
                        amount DECIMAL (10,2) NOT NULL,
                        ts TIMESTAMP NOT NULL,
                        memo VARCHAR (255) NOT NULL
);