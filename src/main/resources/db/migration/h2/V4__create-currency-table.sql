create table currencies (
    id bigserial not null,
    code varchar(3) not null unique,
    name varchar(255) not null unique,
    symbol varchar(10) not null,
    brl_rate numeric(13,6),
    active boolean not null,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    primary key (id)
);

insert into currencies (code, name, symbol, brl_rate, active, created_at, updated_at)
values
    ('BRL', 'Real', 'R$', 1.0, true, '2024-04-30T12:00:00.000001Z', '2024-04-30T12:58:15.359001Z'),
    ('USD', 'Dólar Americano', 'US$', 5.15, true, '2024-04-30T12:00:00.000001Z', '2024-04-30T12:59:21.242001Z'),
    ('BTC', 'Bitcoin', '₿', 315269.47, true, '2024-04-30T12:00:00.000001Z', '2024-04-30T13:03:58.240001Z'),
    ('AUD', 'Dólar Australiano', 'AU$', 3.35, false, '2024-04-30T12:00:00.000001Z', '2024-04-30T13:04:23.059001Z'),
    ('CAD', 'Dólar Canadense', 'C$', 3.75, false, '2024-04-30T12:00:00.000001Z', '2024-04-30T13:04:33.803001Z'),
    ('ETH', 'Ethereum', 'ETH', 15497.60, false, '2024-04-30T12:00:00.000001Z', '2024-04-30T13:04:48.826001Z'),
    ('EUR', 'Euro', '€', 5.51, false, '2024-04-30T12:00:00.000001Z', '2024-04-30T13:05:01.856001Z'),
    ('CHF', 'Fraco Suíço', 'SFr', 5.63, false, '2024-04-30T12:00:00.000001Z', '2024-04-30T13:05:08.347001Z'),
    ('JPY', 'Iene', '¥', 0.033, false, '2024-04-30T12:00:00.000001Z', '2024-04-30T13:05:16.259001Z'),
    ('GBP', 'Libra Esterlina', '£', 6.45, false, '2024-04-30T12:00:00.000001Z', '2024-04-30T13:05:22.169001Z'),
    ('ARS', 'Peso Argentino', '$', 0.0059, true, '2024-04-30T12:00:00.000001Z', '2024-04-30T13:05:27.248001Z')
;