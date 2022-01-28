DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS meals;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id                  INTEGER     PRIMARY KEY     DEFAULT nextval('global_seq'),
    name                VARCHAR                             NOT NULL,
    email               VARCHAR                             NOT NULL,
    password            VARCHAR                             NOT NULL,
    enabled             BOOLEAN     DEFAULT TRUE            NOT NULL,
    registered          TIMESTAMP   DEFAULT now()           NOT NULL,
    calories_per_day    INTEGER     DEFAULT 2000            NOT NULL
);
CREATE UNIQUE INDEX email_unique_idx ON users (email);

CREATE TABLE user_roles
(
    user_id     INTEGER     NOT NULL,
    role        VARCHAR     NOT NULL,
    CONSTRAINT unique_user_role_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE meals
(
    id          INTEGER     PRIMARY KEY     DEFAULT nextval('global_seq'),
    user_id     INTEGER                         NOT NULL,
    date_time   TIMESTAMP                       NOT NULL,
    description VARCHAR                         NOT NULL,
    calories    INTEGER                         NOT NULL,
    CONSTRAINT user_date_time_meal_unique_idx UNIQUE (user_id, date_time),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);