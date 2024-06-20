create table wallets (
    id uuid not null default random_uuid(),
    name varchar(255) not null,
    balance numeric(13,6) not null,
    active boolean,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    currency_id bigint not null,
    user_id uuid not null,
    primary key (id),
    constraint fk_wallets_currencies foreign key (currency_id) references currencies,
    constraint fk_wallets_users foreign key (user_id) references users
);

create table bank_accounts (
    id uuid not null,
    credit_limit numeric(10,2) not null,
    interest_rate numeric (10,2) not null,
    primary key (id),
    constraint fk_bank_accounts_wallets foreign key (id) references wallets
);

create table cash_wallets (
    id uuid not null,
    primary key (id),
    constraint fk_cash_wallets_wallets foreign key (id) references wallets
);

create table credit_cards (
    id uuid not null,
    billing_cycle_date numeric(2) not null,
    due_date numeric (2) not null,
    credit_limit numeric(10,2) not null,
    primary key (id),
    constraint fk_credit_cards_wallets foreign key (id) references wallets
);

create table crypto_wallets (
    id uuid not null,
    primary key (id),
    constraint fk_crypto_wallets_wallets foreign key (id) references wallets
);
