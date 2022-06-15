INSERT INTO user_role(role)
  SELECT 'USER' WHERE NOT EXISTS (
      SELECT role FROM user_role WHERE role='USER'
  );

INSERT INTO user_role(role)
  SELECT 'ADMIN' WHERE NOT EXISTS (
      SELECT role FROM user_role WHERE role='ADMIN'
  );

INSERT INTO user_role(role)
  SELECT 'CREATOR' WHERE NOT EXISTS (
      SELECT role FROM user_role WHERE role='CREATOR'
  );

INSERT INTO chat_user (email, hash_password, login, name, state)
SELECT 'bairamovazat@gmail.com',
       '$2a$10$.6yt2mHF54B..geG.jkkueY8XmiHeczWErmJ8F2s.OElLRts/tgne',
       'admin',
       'admin',
       'CONFIRMED'
    WHERE NOT EXISTS(SELECT name FROM chat_user WHERE name = 'admin');

INSERT INTO users_roles (user_id, role_id)
SELECT (SELECT id FROM chat_user WHERE name = 'admin'),
       (SELECT id FROM user_role WHERE role = 'USER')
    WHERE NOT EXISTS(
        SELECT users_roles.role_id
        FROM chat_user
                 join users_roles on chat_user.id = users_roles.user_id
                 join user_role on users_roles.role_id = user_role.id
        WHERE chat_user.name = 'admin' and user_role.role = 'USER'
    )
ON CONFLICT DO NOTHING;

INSERT INTO users_roles (user_id, role_id)
SELECT (SELECT id FROM chat_user WHERE name = 'admin'),
       (SELECT id FROM user_role WHERE role = 'ADMIN')
    WHERE NOT EXISTS(
        SELECT users_roles.role_id
        FROM chat_user
                 join users_roles on chat_user.id = users_roles.user_id
                 join user_role on users_roles.role_id = user_role.id
        WHERE chat_user.name = 'admin' and user_role.role = 'ADMIN'
    )
ON CONFLICT DO NOTHING;

INSERT INTO users_roles (user_id, role_id)
SELECT (SELECT id FROM chat_user WHERE name = 'admin'),
       (SELECT id FROM user_role WHERE role = 'CREATOR')
    WHERE NOT EXISTS(
        SELECT users_roles.role_id
        FROM chat_user
                 join users_roles on chat_user.id = users_roles.user_id
                 join user_role on users_roles.role_id = user_role.id
        WHERE chat_user.name = 'admin' and user_role.role = 'CREATOR'
    )
ON CONFLICT DO NOTHING;