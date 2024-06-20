create table users (
    id uuid not null default random_uuid(),
    nickname varchar(255),
    email varchar(255) not null unique,
    password varchar(255) not null,
    active boolean not null,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    primary key (id)
);

create table users_roles (
    role integer not null,
    user_id uuid not null
);

alter table users_roles
add constraint fk_users_roles
foreign key (user_id) references users;

insert into users (nickname, email, password, active, created_at, updated_at)
values ('Admin', 'admin@mhc.dev.br', '$2a$10$7f2fJbhQD10By8uHEQiCbOHT1fNhljKbMw4sIBgdE58a3uTV8bGCy', true, current_timestamp at time zone 'UTC', current_timestamp at time zone 'UTC');

insert into users_roles (role, user_id) values
    (1, (select id from users where email like 'admin@mhc.dev.br')),
    (2, (select id from users where email like 'admin@mhc.dev.br'));