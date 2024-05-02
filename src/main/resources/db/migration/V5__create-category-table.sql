create table categories (
    id bigserial not null,
    name varchar(255) not null,
    icon_url varchar(255),
    color varchar(7),
    active boolean not null,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    user_id bigint not null,
    primary key (id)
);

alter table categories add constraint fk_categories_users foreign key (user_id) references users;