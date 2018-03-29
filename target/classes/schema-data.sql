INSERT INTO company(name, date_time_inclusion) VALUES('Ubisoft', current_timestamp);
INSERT INTO company(name, date_time_inclusion) VALUES('Nanco', current_timestamp);
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

INSERT INTO users(name, email, password, enabled, date_time_inclusion) VALUES('Admin', 'admin@email.com', '$2a$10$WGbE4tmdG8wTtrwA5W.wleQtdSnz8F79mkeGPBNT9m6aKk.usR5my', true, current_timestamp);
INSERT INTO users(name, email, password, enabled, date_time_inclusion) VALUES('User', 'user@email.com', '$2a$10$WGbE4tmdG8wTtrwA5W.wleQtdSnz8F79mkeGPBNT9m6aKk.usR5my', true, current_timestamp);

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
  ('@ngul@r', 'WEB_API', 'secret', 'read, write', 'password,refresh_token', null, null, 36000, 36000, null, true);