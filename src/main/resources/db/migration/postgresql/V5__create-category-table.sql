create table categories (
    id uuid not null default uuid_generate_v4(),
    name varchar(255) not null,
    icon varchar(255),
    color varchar(7),
    type numeric(1) not null,
    active boolean not null,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    user_id uuid not null,
    parent_category_id uuid,
    primary key (id),
    constraint fk_categories_users foreign key (user_id) references users,
    constraint fk_categories_parents foreign key (parent_category_id) references categories
);