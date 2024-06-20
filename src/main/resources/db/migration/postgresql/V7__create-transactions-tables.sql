create table transactions (
    id uuid not null default uuid_generate_v4(),
    amount numeric(13,6) not null,
    moment timestamp without time zone not null,
    notes varchar(255),
    type numeric(1) not null,
    method numeric(1) not null,
    current_installment numeric(2) not null,
    active boolean not null,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    user_id uuid not null,
    wallet_id uuid not null,
    primary key (id),
    constraint fk_transactions_users foreign key (user_id) references users,
    constraint fk_transactions_wallets foreign key (wallet_id) references wallets
);

create table transactions_categories (
    transaction_id uuid not null,
    category_id uuid not null,
    amount numeric(13,6) not null,
    type numeric(1) not null,
    primary key (transaction_id, category_id),
    constraint fk_transactions_categories_transactions foreign key (transaction_id) references transactions,
    constraint fk_transactions_categories_categories foreign key (category_id) references categories
);