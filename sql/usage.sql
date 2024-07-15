CREATE TABLE `usage` (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       account VARCHAR(255) NOT NULL,
                       compute_credits INT NOT NULL,
                       minutes INT NOT NULL,
                       cpu INT NOT NULL,
                       ram INT NOT NULL,
                       storage INT NOT NULL,
                       price DOUBLE NOT NULL
);