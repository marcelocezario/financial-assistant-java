insert into users (id, nickname, email, password, active, created_at, updated_at)
values ('cf1e2d3a-98ff-4260-87f1-315f1c5e400d', 'Basic', 'basic@mhc.dev.br', '$2a$10$YsIiIawW74c6ljbEJ18sn.sTSwfizGAZ2jQWXuyo295TKx5oT439a', true, current_timestamp at time zone 'UTC', current_timestamp at time zone 'UTC');

insert into users_roles (role, user_id)
values (2, (select id from users where email like 'basic@mhc.dev.br'));