CREATE TABLE IF NOT EXISTS users (
    id          UUID         PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    created_at  TIMESTAMP    NOT NULL
);

CREATE TABLE IF NOT EXISTS accounts (
    id          UUID           PRIMARY KEY,
    user_id     UUID           NOT NULL,
    balance     NUMERIC(19, 4) NOT NULL,
    created_at  TIMESTAMP      NOT NULL
);

CREATE TABLE IF NOT EXISTS transactions (
    id          UUID           PRIMARY KEY,
    account_id  UUID           NOT NULL,
    type        VARCHAR(50)    NOT NULL,
    amount      NUMERIC(19, 4) NOT NULL,
    description VARCHAR(255),
    created_at  TIMESTAMP      NOT NULL
);
