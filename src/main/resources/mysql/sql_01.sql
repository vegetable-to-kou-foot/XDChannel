USE xdc;

DROP TABLE Account;
DROP TABLE AidSsid;

CREATE TABLE Account(
aid BIGINT PRIMARY KEY AUTO_INCREMENT,
accName VARCHAR(30) NOT NULL,
userPswd VARCHAR(20) NOT NULL,
email VARCHAR(30) NOT NULL
);

CREATE TABLE AidSsid(
aid BIGINT PRIMARY KEY,
ssid CHAR(16) NOT NULL,
timestp BIGINT NOT NULL
);