CREATE TABLE actions (
                        id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        owner VARCHAR (255) NOT NULL,
                        ip VARCHAR (255) NOT NULL,
                        device VARCHAR (255) NOT NULL,
                        action_date DATETIME NOT NULL,
                        action VARCHAR (255) NOT NULL
);