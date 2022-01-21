DELETE
FROM user_roles;
DELETE
FROM users;
ALTER SEQUENCE general_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('user', 'user@gmail.com', 'userPass'),
       ('admin', 'admin@gmail.com', 'adminPass');

INSERT INTO user_roles (user_id, role)
VALUES (100000, 'USER'),
       (100001, 'ADMIN');