CREATE SCHEMA `inventory2` ;
USE inventory2;

CREATE TABLE IF NOT EXISTS section (
	code varchar(50),
    size float,
    free float,
    value float,
    imageDIR varchar(1000),
    primary key (code)
);

CREATE TABLE IF NOT EXISTS product (
	code varchar(50),
    type varchar(50),
	name varchar(50),
    brand varchar(50),
    size float,
    price float,
    
    quantity int,
    location varchar(50),
	dateIN varchar(50),
	imageDIR varchar(1000),
    
	PRIMARY KEY (code),
    foreign key (location) references section(code)
);

CREATE TABLE IF NOT EXISTS nall (
	code varchar(50),
    primary key (code)
);

-- TRIGGERS
-- TRIGGER FOR INSERT

DELIMITER //
CREATE TRIGGER before_product_insert
BEFORE INSERT ON product
FOR EACH ROW
BEGIN
    DECLARE product_occupation FLOAT;
    SELECT NEW.size * NEW.quantity INTO product_occupation;
    
    IF product_occupation > (
        SELECT free FROM section WHERE code = NEW.location
    ) THEN
        SIGNAL SQLSTATE '45000' 
            SET MESSAGE_TEXT = 'Product occupation exceeds section free limit';
    ELSE
        UPDATE section
        SET free = free - product_occupation, value = value + NEW.price * NEW.quantity
        WHERE code = NEW.location;
    END IF;
END //
DELIMITER ;

----------------------------------------------------
-- TRIGGER FOR DELETE
DELIMITER //
CREATE TRIGGER after_product_delete
AFTER DELETE ON product
FOR EACH ROW
BEGIN
	-- Calculate space * quantity for deleted product
    DECLARE product_occupation FLOAT;
    SET product_occupation = OLD.size * OLD.quantity;
    
    -- Update the section table to decement the free by 
    UPDATE section
    SET free = free + product_occupation, value = value - OLD.price * OLD.quantity
    WHERE code= OLD.location;
END //
DELIMITER ;
------------------------------------------------------
-- TRIGGER FOR UPDATE
DELIMITER //
CREATE TRIGGER before_product_update
BEFORE UPDATE ON product 
FOR EACH ROW
BEGIN
    -- Update old section
    DECLARE old_product_occupation FLOAT;
    SELECT OLD.size * OLD.quantity INTO old_product_occupation;
    
    UPDATE section
    SET free = free + old_product_occupation, value = value - OLD.price * OLD.quantity
    WHERE code = OLD.location;
    
    -- Check
    IF 	(SELECT free FROM section WHERE code = NEW.location) >= NEW.size * NEW.quantity THEN
        UPDATE section
        SET free = free - NEW.size * NEW.quantity, value = value + NEW.price * NEW.quantity
        WHERE code = NEW.location;
    ELSE
        -- Handle the case when the update cannot be performed
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Cannot update: Insufficient space in the section.';
    END IF;
END //
DELIMITER ;