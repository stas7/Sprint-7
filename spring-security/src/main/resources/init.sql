CREATE TABLE users
(
    user_id  serial primary key,
    email    varchar unique NOT NULL,
    username varchar        NOT NULL,
    password varchar        NOT NULL,
    enabled  int DEFAULT NULL
);

CREATE TABLE role
(
    role_id serial primary key,
    name    varchar NOT NULL
);

CREATE TABLE users_roles
(
    user_id integer REFERENCES users (user_id),
    role_id integer REFERENCES role (role_id)
);

INSERT INTO role (name)
VALUES ('USER');
INSERT INTO role (name)
VALUES ('APP_ROLE');
INSERT INTO role (name)
VALUES ('API_ROLE');
INSERT INTO role (name)
VALUES ('ADMIN');

-- пароль test
INSERT INTO users (username, password, enabled, email)
VALUES ('user', '$2a$10$ZU5OfsXBajd75/eiVtInqe1xstihShQWVpozDIxECo9sFe4SlZake', '1', 'user@gmail.com');

INSERT INTO users (username, password, enabled, email)
VALUES ('app', '$2a$10$ZU5OfsXBajd75/eiVtInqe1xstihShQWVpozDIxECo9sFe4SlZake', '1', 'app@gmail.com');

INSERT INTO users (username, password, enabled, email)
VALUES ('api', '$2a$10$ZU5OfsXBajd75/eiVtInqe1xstihShQWVpozDIxECo9sFe4SlZake', '1', 'api@gmail.com');

INSERT INTO users (username, password, enabled, email)
VALUES ('admin', '$2a$10$ZU5OfsXBajd75/eiVtInqe1xstihShQWVpozDIxECo9sFe4SlZake', '1', 'admin@gmail.com');


-- user has role USER
INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1);

-- app has role ROLE_APP
INSERT INTO users_roles (user_id, role_id)
VALUES (2, 2);

-- api has role ROLE_API
INSERT INTO users_roles (user_id, role_id)
VALUES (3, 3);

-- admin has role ADMIN
INSERT INTO users_roles (user_id, role_id)
VALUES (4, 4);