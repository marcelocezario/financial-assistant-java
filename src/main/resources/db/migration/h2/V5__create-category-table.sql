create table categories (
    id uuid not null default random_uuid(),
    name varchar(255) not null,
    icon varchar(255),
    color varchar(7),
    active boolean not null,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    user_id uuid not null,
    primary key (id),
    constraint fk_categories_users foreign key (user_id) references users
);
