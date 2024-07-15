CREATE TABLE deployments (
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            owner VARCHAR(255) NOT NULL,
                            code VARCHAR(255) NOT NULL,
                            name VARCHAR(255) NOT NULL,
                            description VARCHAR(255),
                            image VARCHAR(255),
                            price DOUBLE NOT NULL,
                            category VARCHAR(255),
                            quantity INT NOT NULL,
                            inventory_status VARCHAR(255),
                            rating INT NOT NULL,
                            endpoint VARCHAR(255) NOT NULL,
                            project VARCHAR(255) NOT NULL,
                            sha VARCHAR(255) NOT NULL
);
