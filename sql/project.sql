CREATE TABLE projects (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         owner VARCHAR(255) NOT NULL,
                         name VARCHAR(255) NOT NULL,
                         creation_date TIMESTAMP NOT NULL,
                         size DOUBLE NOT NULL,
                         description TEXT NOT NULL,
                         endpoint VARCHAR(255) NOT NULL,
                         last_staging_deployment TIMESTAMP NOT NULL
);