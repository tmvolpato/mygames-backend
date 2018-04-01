INSERT INTO company(name, date_time_inclusion) VALUES('Ubisoft', current_timestamp);
INSERT INTO company(name, date_time_inclusion) VALUES('Bandai Namco', current_timestamp);
INSERT INTO company(name, date_time_inclusion) VALUES('Capcom', current_timestamp);
INSERT INTO company(name, date_time_inclusion) VALUES('Konami', current_timestamp);
INSERT INTO company(name, date_time_inclusion) VALUES('Nintendo', current_timestamp);
INSERT INTO company(name, date_time_inclusion) VALUES('Bethesda', current_timestamp);

INSERT INTO genre(name, date_time_inclusion) VALUES('Ação', current_timestamp);
INSERT INTO genre(name, date_time_inclusion) VALUES('Aventura', current_timestamp);
INSERT INTO genre(name, date_time_inclusion) VALUES('Estratégia', current_timestamp);
INSERT INTO genre(name, date_time_inclusion) VALUES('RPG', current_timestamp);
INSERT INTO genre(name, date_time_inclusion) VALUES('Esporte', current_timestamp);
INSERT INTO genre(name, date_time_inclusion) VALUES('Corrida', current_timestamp);
INSERT INTO genre(name, date_time_inclusion) VALUES('Simulação', current_timestamp);
INSERT INTO genre(name, date_time_inclusion) VALUES('Outros gêneros', current_timestamp);

INSERT INTO platform(name, date_time_inclusion) VALUES ('Xbox One', current_timestamp);
INSERT INTO platform(name, date_time_inclusion) VALUES('Xbox One X', current_timestamp);
INSERT INTO platform(name, date_time_inclusion) VALUES('Playstation 3', current_timestamp);
INSERT INTO platform(name, date_time_inclusion) VALUES('Playstation 4', current_timestamp);
INSERT INTO platform(name, date_time_inclusion) VALUES('Playstation 4 Pro', current_timestamp);
INSERT INTO platform(name, date_time_inclusion) VALUES('Nintendo Switch', current_timestamp);

INSERT INTO role(name, date_time_inclusion) VALUES ('ADMIN', current_timestamp);
INSERT INTO role(name, date_time_inclusion) VALUES ('USER', current_timestamp);

INSERT INTO privilege(name, date_time_inclusion) VALUES ('READ_PRIVILEGE', current_timestamp);
INSERT INTO privilege(name, date_time_inclusion) VALUES ('WRITE_PRIVILEGE', current_timestamp);

INSERT INTO users(name, email, password, enabled, date_time_inclusion) VALUES('Admin', 'admin@email.com', '{bcrypt}$2a$10$bufDnfixY3Hc4sS3kyFxY.1SKhoGx.KDnuy5V/WE3Xxb2v/p19F8C', true, current_timestamp);
INSERT INTO users(name, email, password, enabled, date_time_inclusion) VALUES('User', 'user@email.com', '{bcrypt}$2a$10$bufDnfixY3Hc4sS3kyFxY.1SKhoGx.KDnuy5V/WE3Xxb2v/p19F8C', true, current_timestamp);

INSERT INTO user_has_role(user_id, role_id) VALUES (1, 1);
INSERT INTO user_has_role(user_id, role_id) VALUES (1, 2);
INSERT INTO user_has_role(user_id, role_id) VALUES (2, 2);

INSERT INTO role_has_privilege(role_id, privilege_id) VALUES (1, 1);
INSERT INTO role_has_privilege(role_id, privilege_id) VALUES (1, 2);

INSERT INTO role_has_privilege(role_id, privilege_id) VALUES (2, 1);
INSERT INTO role_has_privilege(role_id, privilege_id) VALUES (2, 2);

INSERT INTO oauth_client_details(client_id, resource_ids, client_secret, scope,
                                 authorized_grant_types, web_server_redirect_uri, authorities,
                                 access_token_validity, refresh_token_validity, additional_information, autoapprove) VALUES
  ('@ngul@r', 'MY_GAMES_ID', 'secret', 'read, write', 'password, refresh_token', null, null, 36000, 36000, null, true);

INSERT INTO game(title, price, platform_id, company_id, genre_id, user_id, date_time_inclusion) VALUES ('Dark SOULS I', 120.00, 3, 2, 4, 1, CURRENT_DATE);
INSERT INTO game(title, price, platform_id, company_id, genre_id, user_id, date_time_inclusion) VALUES ('Dark SOULS II', 60.00, 3, 2, 4, 1, CURRENT_DATE);
INSERT INTO game(title, price, platform_id, company_id, genre_id, user_id, date_time_inclusion) VALUES ('Dark SOULS III', 145.00, 4, 2, 4, 1, CURRENT_DATE);

INSERT INTO game(title, price, platform_id, company_id, genre_id, user_id, date_time_inclusion) VALUES ('Monster Hunter', 200.00, 4, 3, 4, 1, CURRENT_DATE);
INSERT INTO game(title, price, platform_id, company_id, genre_id, user_id, date_time_inclusion) VALUES ('Pro Evolution 2017', 150.00, 4, 4, 5, 1, CURRENT_DATE);