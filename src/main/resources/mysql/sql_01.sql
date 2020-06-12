USE xdc;

DROP TABLE Account;

CREATE TABLE Account(
aid BIGINT PRIMARY KEY AUTO_INCREMENT,
accName VARCHAR(30) NOT NULL,
userPswd CHAR(32) NOT NULL,
email VARCHAR(30) NOT NULL,
checkInfo CHAR(32) NOT NULL
);

DROP TABLE AidSsid;

CREATE TABLE AidSsid(
aid BIGINT PRIMARY KEY,
ssid CHAR(16) NOT NULL,
timestp BIGINT NOT NULL
);

DROP TABLE UserInfo;

CREATE TABLE UserInfo(
aid BIGINT PRIMARY KEY,
userInfo VARCHAR(1024) NOT NULL,
userTag VARCHAR(1024) NOT NULL,
profilePic VARCHAR(300)
);

DROP TABLE AccountTag;

CREATE TABLE AccountTag(
atid BIGINT PRIMARY KEY AUTO_INCREMENT,
aTagName VARCHAR(64) NOT NULL,
aTagInfo VARCHAR(1024) NOT NULL
);

DROP TABLE UserBroadcast;

CREATE TABLE UserBroadcast(
bid BIGINT PRIMARY KEY AUTO_INCREMENT,
fid BIGINT NOT NULL,
aid BIGINT NOT NULL,
bcScript VARCHAR(2048) NOT NULL,
likeIt INT NOT NULL,
review VARCHAR(10000) NOT NULL,
bcTag VARCHAR(256) NOT NULL,
timestp BIGINT NOT NULL,
limits VARCHAR(2048) NOT NULL
);

DROP TABLE BroadcastTag;

CREATE TABLE BroadcastTag(
btid BIGINT PRIMARY KEY AUTO_INCREMENT,
bTagName VARCHAR(64) NOT NULL,
bTagInfo VARCHAR(10000) NOT NULL
);

DROP TABLE Follow;

CREATE TABLE Follow(
aid BIGINT PRIMARY KEY,
followInfo VARCHAR(10000) NOT NULL
);

DROP TABLE BeFollowed;

CREATE TABLE BeFollowed(
aid BIGINT PRIMARY KEY,
beFollowedInfo VARCHAR(10000) NOT NULL
);