DROP TABLE IF EXISTS BOOKS;
CREATE TABLE BOOKS(ID BIGINT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(255), AUTHOR_ID INT);

DROP TABLE IF EXISTS AUTHORS;
CREATE TABLE AUTHORS(ID INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(255));

DROP TABLE IF EXISTS GENRES;
CREATE TABLE GENRES(ID INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(255));

DROP TABLE IF EXISTS GENRES_BOOKS;
CREATE TABLE GENRES_BOOKS(GENRE_ID INT, BOOK_ID INT);