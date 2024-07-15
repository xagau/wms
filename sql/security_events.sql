CREATE TABLE security_events (
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                owner VARCHAR(255) NOT NULL,
                                ip VARCHAR(255) NOT NULL,
                                country_code VARCHAR(255) NOT NULL,
                                action VARCHAR(255) NOT NULL,
                                ts TIMESTAMP NOT NULL,
                                device VARCHAR(255) NOT NULL
);