ALTER TABLE
    tb_food ADD (flag TINYINT(1));
ALTER TABLE
    tb_food modify flag TINYINT(1) after position;    
    
ALTER TABLE
    tb_food ADD (bar_code VARCHAR(100)); 
ALTER TABLE
    tb_food modify bar_code VARCHAR(100) after sn;    

ALTER TABLE
    tb_transaction ADD (type VARCHAR(100)); 
ALTER TABLE
    tb_transaction modify type VARCHAR(100) after invoice;  
    