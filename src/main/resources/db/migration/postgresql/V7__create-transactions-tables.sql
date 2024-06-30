create table transactions_parents (
    id uuid not null default uuid_generate_v4(),
    event_moment timestamp without time zone not null,
    total_of_installments numeric(3) not null,
    notes varchar(255),
    active boolean not null,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    primary key (id)
);

create table transactions (
    id uuid not null default uuid_generate_v4(),
    amount numeric(13,6) not null,
    due_date date not null,
    payment_moment timestamp without time zone,
    notes varchar(255),
    type numeric(1) not null,
    method numeric(1) not null,
    current_installment numeric(3) not null,
    active boolean not null,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    user_id uuid not null,
    wallet_id uuid not null,
    transaction_parent_id uuid not null,
    primary key (id),
    constraint fk_transactions_users foreign key (user_id) references users,
    constraint fk_transactions_wallets foreign key (wallet_id) references wallets,
    constraint fk_transactions_parents foreign key (transaction_parent_id) references transactions_parents
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

create table transactions_subscriptions (
    id uuid not null default uuid_generate_v4(),
    amount numeric(13,6) not null,
    time_interval numeric(1) not null,
    start_date date not null,
    end_date date,
    active boolean not null,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    transaction_parent_id uuid not null,
    primary key (id),
    constraint fk_transactions_subscriptions_parents foreign key (transaction_parent_id) references transactions_parents
);