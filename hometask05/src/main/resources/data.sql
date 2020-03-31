INSERT INTO GENRES (id, name)
VALUES (0, 'Historical books');
INSERT INTO GENRES (id, name)
VALUES (1, 'Military books');

INSERT INTO AUTHORS (id, name)
VALUES (0, 'Nikolai Karamzin');
INSERT INTO AUTHORS (id, name)
VALUES (1, 'Valentin Pikul');

INSERT INTO BOOKS (id, name, author_id)
VALUES (0, 'Requiem to PQ-17', 1);
INSERT INTO BOOKS (id, name, author_id)
VALUES (1, 'History of the Russian State', 0);

INSERT INTO GENRES_BOOKS (genre_id, book_id)
VALUES (0, 0);
INSERT INTO GENRES_BOOKS (genre_id, book_id)
VALUES (1, 0);
INSERT INTO GENRES_BOOKS (genre_id, book_id)
VALUES (0, 1);