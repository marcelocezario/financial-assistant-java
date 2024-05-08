create table currencies (
    id bigserial not null,
    name varchar(255) not null unique,
    symbol varchar(10) not null,
    code varchar(3) not null unique,
    price_in_brl numeric(13,6),
    active boolean not null,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    primary key (id)
);

insert into currencies (name, symbol, code, price_in_brl, active, created_at, updated_at)
values
    ('Real', 'R$', 'BRL', 1.0, true, '2024-04-30T12:00:00.000001Z', '2024-04-30T12:58:15.359001Z'),
    ('Dólar', 'US$', 'USD', 5.15, true, '2024-04-30T12:00:00.000001Z', '2024-04-30T12:59:21.242001Z'),
    ('Bitcoin', '₿', 'BTC', 315269.47, false, '2024-04-30T12:00:00.000001Z', '2024-04-30T13:03:58.240001Z'),
    ('Dólar Australiano', 'AU$', 'AUD', 3.35, false, '2024-04-30T12:00:00.000001Z', '2024-04-30T13:04:23.059001Z'),
    ('Dólar Canadense', 'C$', 'CAD', 3.75, false, '2024-04-30T12:00:00.000001Z', '2024-04-30T13:04:33.803001Z'),
    ('Ethereum', 'ETH', 'ETH', 15497.60, false, '2024-04-30T12:00:00.000001Z', '2024-04-30T13:04:48.826001Z'),
    ('Euro', '€', 'EUR', 5.51, false, '2024-04-30T12:00:00.000001Z', '2024-04-30T13:05:01.856001Z'),
    ('Franco Suíço', 'SFr', 'CHF', 5.63, false, '2024-04-30T12:00:00.000001Z', '2024-04-30T13:05:08.347001Z'),
    ('Iene', '¥', 'JPY', 0.033, false, '2024-04-30T12:00:00.000001Z', '2024-04-30T13:05:16.259001Z'),
    ('Libra Esterlina', '£', 'GBP', 6.45, false, '2024-04-30T12:00:00.000001Z', '2024-04-30T13:05:22.169001Z'),
    ('Peso Argentino', '$', 'ARS', 0.0059, false, '2024-04-30T12:00:00.000001Z', '2024-04-30T13:05:27.248001Z')
;