DELETE
FROM user_roles;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('user', 'user@gmail.com', 'userPass'),
       ('admin', 'admin@gmail.com', 'adminPass');

INSERT INTO user_roles (user_id, role)
VALUES (100000, 'USER'),
       (100001, 'ADMIN');

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2021-01-30 08:00:00.000', 'Завтрак', 500, 100000),
       ('2021-01-30 13:00:00.000', 'Обед', 1000, 100000),
       ('2021-01-30 19:00:00.000', 'Ужин', 500, 100000),
       ('2021-01-31 00:00:00.000', 'Еда на граничное значение', 100, 100000),
       ('2021-01-31 08:00:00.000', 'Завтрак', 500, 100000),
       ('2021-01-31 13:00:00.000', 'Обед', 1000, 100000),
       ('2021-01-31 19:00:00.000', 'Ужин', 410, 100000),
       ('2015-07-15 08:00:00.000', 'Завтрак админ', 500, 100001),
       ('2015-01-31 13:00:00.000', 'Обед админ', 1000, 100001);