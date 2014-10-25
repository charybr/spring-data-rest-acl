-- default admin user, pwd: admin123
INSERT INTO userentity(id, enabled, password, username) VALUES(nextval('hibernate_sequence'),'true','$2a$10$Isti6gH/65twVovOwzDz5eryiJeRv3OLPwsihq9lTcij5UG/wIiVO','admin');

INSERT INTO roleentity(id, authority) VALUES(nextval('hibernate_sequence'),'ROLE_ADMIN');

INSERT INTO userentity_roleentity(users_id, roles_id) VALUES(1,2);
