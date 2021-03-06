DROP TABLE IF EXISTS AUTHORS;
CREATE TABLE AUTHORS(ID BIGINT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(255));

DROP TABLE IF EXISTS BOOKS;
CREATE TABLE BOOKS(ID BIGINT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(255), AUTHOR_ID BIGINT);

DROP TABLE IF EXISTS GENRES;
CREATE TABLE GENRES(ID BIGINT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(255));

DROP TABLE IF EXISTS GENRES_BOOKS;
CREATE TABLE GENRES_BOOKS(GENRE_ID BIGINT REFERENCES GENRES(ID), BOOK_ID BIGINT, UNIQUE (GENRE_ID, BOOK_ID));

ALTER TABLE BOOKS
    ADD FOREIGN KEY (AUTHOR_ID)
        REFERENCES AUTHORS(ID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE GENRES_BOOKS
    ADD FOREIGN KEY (GENRE_ID)
        REFERENCES GENRES(ID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE GENRES_BOOKS
    ADD FOREIGN KEY (BOOK_ID)
        REFERENCES BOOKS(ID) ON DELETE CASCADE ON UPDATE CASCADE;