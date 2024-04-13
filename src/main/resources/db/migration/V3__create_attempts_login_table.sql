create table login_attempts (
    id bigserial not null,
    username varchar(255) not null unique,
    failed_attempts integer not null,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    primary key (id)
);