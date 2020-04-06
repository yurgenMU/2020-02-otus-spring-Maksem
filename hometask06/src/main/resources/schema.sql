DROP TABLE IF EXISTS AUTHORS;
CREATE TABLE AUTHORS(ID BIGINT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(255));

DROP TABLE IF EXISTS BOOKS;
CREATE TABLE BOOKS(ID BIGINT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(255), AUTHOR_ID BIGINT REFERENCES AUTHORS(ID));

DROP TABLE IF EXISTS GENRES;
CREATE TABLE GENRES(ID BIGINT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(255));

DROP TABLE IF EXISTS GENRES_BOOKS;
CREATE TABLE GENRES_BOOKS(GENRE_ID BIGINT REFERENCES GENRES(ID), BOOK_ID BIGINT REFERENCES BOOKS(ID));

DROP TABLE IF EXISTS COMMENTARIES;
CREATE TABLE COMMENTARIES(ID BIGINT PRIMARY KEY AUTO_INCREMENT, CONTENT VARCHAR(1024),
BOOK_ID BIGINT REFERENCES BOOKS(ID), DATE TIMESTAMP);