DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories) VALUES

  (100000, TO_TIMESTAMP('2015-05-30 10:00', 'YYYY-MM-DD HH24:MI'), 'Завтрак', 500),
  (100000, TO_TIMESTAMP('2015-05-30 13:00', 'YYYY-MM-DD HH24:MI'), 'Обед', 1000),
  (100000, TO_TIMESTAMP('2015-05-30 20:00', 'YYYY-MM-DD HH24:MI'), 'Ужин', 500),
  (100000, TO_TIMESTAMP('2015-05-31 10:00', 'YYYY-MM-DD HH24:MI'), 'Завтрак', 1000),
  (100000, TO_TIMESTAMP('2015-05-31 13:00', 'YYYY-MM-DD HH24:MI'), 'Обед', 500),
  (100000, TO_TIMESTAMP('2015-05-31 20:00', 'YYYY-MM-DD HH24:MI'), 'Ужин', 510),

  (100001, TO_TIMESTAMP('2015-06-01 14:00', 'YYYY-MM-DD HH24:MI'), 'Админ ланч', 510),
  (100001, TO_TIMESTAMP('2015-06-01 21:00', 'YYYY-MM-DD HH24:MI'), 'Админ ужин', 1500);
