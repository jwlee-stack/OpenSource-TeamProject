###############################################
DROP DATABASE IF EXISTS userdb;

CREATE DATABASE IF NOT EXISTS userdb 
    DEFAULT CHARACTER SET utf8 
    DEFAULT COLLATE utf8_general_ci;

USE userdb;

CREATE TABLE user (
    id INT NOT NULL AUTO_INCREMENT,
    user_id VARCHAR(20) NOT NULL,
    user_pw VARCHAR(100) NOT NULL,
    user_nickname VARCHAR(20) NOT NULL,
    user_score_1 INT,
    user_score_2 INT,
    user_score_3 INT,
    PRIMARY KEY (id)
) ENGINE = InnoDB
    DEFAULT CHARACTER SET utf8 
    DEFAULT COLLATE utf8_general_ci;

###############################################

INSERT INTO user VALUES
 (null, 'admin', 'admin', '관리자', 10, 20, 30),
 (null, 'test', 'test123', '최강지존', 10, 20, 30);
