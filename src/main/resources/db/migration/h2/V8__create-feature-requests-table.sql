create table feature_requests (
    id bigserial not null,
    description varchar(255) not null,
    rating integer not null,
    developed boolean not null,
    approved boolean not null,
    active boolean not null,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    primary key (id)
);

create table  feature_requests_translation (
    id bigserial not null,
    feature_request_id bigint not null,
    language_code varchar(5) not null,
    title varchar(255) not null,
    description varchar(255) not null,
    active boolean not null,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    primary key (id),
    constraint fk_feature_requests_translation foreign key (feature_request_id) references feature_requests,
    unique (feature_request_id, language_code)
);