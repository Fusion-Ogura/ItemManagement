DROP TABLE IF EXISTS stock;  
DROP TABLE IF EXISTS item;  

CREATE TABLE item (  
    id BIGINT PRIMARY KEY AUTO_INCREMENT,  
    name VARCHAR(255) NOT NULL,  
    price DECIMAL(19,2) NOT NULL  
);  

CREATE TABLE stock (  
    id BIGINT PRIMARY KEY AUTO_INCREMENT,  
    quantity INT NOT NULL,  
    item_id BIGINT UNIQUE NOT NULL,  
    FOREIGN KEY (item_id) REFERENCES item(id)  
);  
