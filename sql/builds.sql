CREATE TABLE builds (
                       id INTEGER PRIMARY KEY AUTO_INCREMENT,
                       build_number INTEGER,
                       project VARCHAR(255),
                       owner VARCHAR(255),
                       cpu INTEGER,
                       ram INTEGER,
                       storage INTEGER,
                       transformations INTEGER
);