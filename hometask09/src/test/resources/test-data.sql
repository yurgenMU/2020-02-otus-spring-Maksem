INSERT INTO GENRES (id, name)
VALUES (1, 'Historical books');
INSERT INTO GENRES (id, name)
VALUES (2, 'Military books');
INSERT INTO GENRES (id, name)
VALUES (3, 'Russian Classic');
INSERT INTO GENRES (id, name)
VALUES (4, 'Computer science');
INSERT INTO GENRES (id, name)
VALUES (5, 'Philosophy');

INSERT INTO AUTHORS (id, name)
VALUES (1, 'Nikolai Karamzin');
INSERT INTO AUTHORS (id, name)
VALUES (2, 'Valentin Pikul');
INSERT INTO AUTHORS (id, name)
VALUES (3, 'Alexander Pushkin');
INSERT INTO AUTHORS (id, name)
VALUES (4, 'Robert Martin');
INSERT INTO AUTHORS (id, name)
VALUES (5, 'Fyodor Dostoevsky');

INSERT INTO BOOKS (id, name, author_id)
VALUES (1, 'Requiem to PQ-17', 2);
INSERT INTO BOOKS (id, name, author_id)
VALUES (2, 'History of the Russian State', 1);
INSERT INTO BOOKS (id, name, author_id)
VALUES (3, 'Clean Code', 4);
INSERT INTO BOOKS (id, name, author_id)
VALUES (4, 'The Idiot', 5);
INSERT INTO BOOKS (id, name, author_id)
VALUES (5, 'Boris Godunov', 3);
INSERT INTO BOOKS (id, name, author_id)
VALUES (6, 'Eugene Onegin', 3);

INSERT INTO GENRES_BOOKS (genre_id, book_id)
VALUES (1, 1);
INSERT INTO GENRES_BOOKS (genre_id, book_id)
VALUES (2, 1);
INSERT INTO GENRES_BOOKS (genre_id, book_id)
VALUES (1, 5);
INSERT INTO GENRES_BOOKS (genre_id, book_id)
VALUES (3, 5);
INSERT INTO GENRES_BOOKS (genre_id, book_id)
VALUES (4, 3);
INSERT INTO GENRES_BOOKS (genre_id, book_id)
VALUES (3, 6);
INSERT INTO GENRES_BOOKS (genre_id, book_id)
VALUES (1, 2);
INSERT INTO GENRES_BOOKS (genre_id, book_id)
VALUES (3, 4);
INSERT INTO GENRES_BOOKS (genre_id, book_id)
VALUES (5, 4);

INSERT INTO COMMENTARIES VALUES (1, 'Nice Book!', 1, '2016-09-06 14:41:05.0+00');
INSERT INTO COMMENTARIES VALUES (2, 'Interesting!', 2, '2017-07-03 12:37:25.0+00');