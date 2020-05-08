INSERT INTO GENRES (id, name)
VALUES (1, 'Historical books');
INSERT INTO GENRES (id, name)
VALUES (2, 'Military books');

INSERT INTO AUTHORS (id, name)
VALUES (1, 'Nikolai Karamzin');
INSERT INTO AUTHORS (id, name)
VALUES (2, 'Valentin Pikul');

INSERT INTO BOOKS (id, name, author_id)
VALUES (1, 'Requiem to PQ-17', 2);
INSERT INTO BOOKS (id, name, author_id)
VALUES (2, 'History of the Russian State', 1);

INSERT INTO GENRES_BOOKS (genre_id, book_id)
VALUES (1, 1);
INSERT INTO GENRES_BOOKS (genre_id, book_id)
VALUES (2, 1);
INSERT INTO GENRES_BOOKS (genre_id, book_id)
VALUES (1, 2);