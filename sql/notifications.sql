CREATE TABLE `notifications` (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       ntype VARCHAR(255) NOT NULL,
                       content VARCHAR(255) NOT NULL,
                       phone VARCHAR(255) NOT NULL,
                       owner VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       ts TIMESTAMP NOT NULL,
                       status INT NOT NULL
                       
);