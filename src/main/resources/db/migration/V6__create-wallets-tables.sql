create table wallets (
    id bigserial not null,
    name varchar(255) not null,
    balance numeric(13,6) not null,
    active boolean,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    currency_id bigint not null,
    user_id bigint not null,
    primary key (id)
);

alter table wallets add constraint fk_wallets_currencies foreign key (currency_id) references currencies;
alter table wallets add constraint fk_wallets_users foreign key (user_id) references users;

create table bank_accounts (
    id bigint not null,
    credit_limit numeric(10,2) not null,
    interest_rate numeric (10,2) not null,
    primary key (id)
);

create table cash_wallets (
    id bigint not null,
    primary key (id)
);

create table credit_cards (
    id bigint not null,
    billing_cycle_date numeric(2) not null,
    due_date numeric (2) not null,
    credit_limit numeric(10,2) not null,
    primary key (id)
);

create table crypto_wallets (
    id bigint not null,
    primary key (id)
);