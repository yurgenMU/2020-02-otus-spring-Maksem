INSERT INTO AUTHORITIES (id, name) VALUES (1,'ADMIN');
INSERT INTO AUTHORITIES (id, name) VALUES (2, 'USER');
INSERT INTO USERS_AUTHORITIES (user_id, authority_id) VALUES (1, 1);
INSERT INTO USERS_AUTHORITIES (user_id, authority_id) VALUES (2, 2);
INSERT INTO USERS (ID, USERNAME, PASSWORD, DATE) VALUES (1,'Ivan','$2y$12$kWbvXPvhPNHoEIdlwHfEsOS3EjkdJNTZNaF3AiBwbRwZ1.PIS4m9G','2019-07-03 12:37:25.0+00');
INSERT INTO USERS (ID, USERNAME, PASSWORD, DATE) VALUES (2,'Petr','$2y$12$kWbvXPvhPNHoEIdlwHfEsOS3EjkdJNTZNaF3AiBwbRwZ1.PIS4m9G','2019-11-15 22:14:54');

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