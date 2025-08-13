CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE  IF NOT EXISTS users(
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       username TEXT NOT NULL UNIQUE,
                       email TEXT NOT NULL UNIQUE,
                       password_hash TEXT NOT NULL,
                       roles TEXT[] NOT NULL DEFAULT ARRAY['ROLE_USER']::TEXT[],
                       enabled BOOLEAN NOT NULL DEFAULT TRUE,
                       created_at timestamptz NOT NULL DEFAULT now(),
                       updated_at timestamptz NOT NULL DEFAULT now()
);

CREATE INDEX idx_users_email ON users(email);

CREATE TABLE IF NOT EXISTS accounts(
                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                          account_number TEXT NOT NULL UNIQUE,
                          currency TEXT NOT NULL DEFAULT 'USD',
                          balance numeric(18,2) NOT NULL DEFAULT 0.00,
                          created_at timestamptz NOT NULL DEFAULT now()
);

CREATE INDEX idx_accounts_user_id ON accounts(user_id);

CREATE TABLE IF NOT EXISTS transactions (
                              id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                              account_id UUID NOT NULL REFERENCES accounts(id) ON DELETE CASCADE,
                              amount numeric(18,2) NOT NULL,
                              currency TEXT NOT NULL DEFAULT 'USD',
                              merchant TEXT,
                              description TEXT,
                              happened_at timestamptz NOT NULL DEFAULT now(),
                              type TEXT NOT NULL CHECK (type IN ('DEBIT','CREDIT')),
                              created_at timestamptz NOT NULL DEFAULT now()
);

CREATE INDEX idx_transactions_account_id ON transactions(account_id);
CREATE INDEX idx_transactions_happened_at ON transactions(happened_at);

CREATE TABLE IF NOT EXISTS roundups (
                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          transaction_id UUID NOT NULL REFERENCES transactions(id) ON DELETE CASCADE,
                          user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                          spare_amount numeric(18,2) NOT NULL CHECK (spare_amount >= 0.00),
                          currency TEXT NOT NULL DEFAULT 'USD',
                          created_at timestamptz NOT NULL DEFAULT now(),
                          allocated BOOLEAN NOT NULL DEFAULT FALSE,
                          batch_id UUID NULL
);

CREATE UNIQUE INDEX ux_roundups_transaction_id ON roundups(transaction_id);
CREATE INDEX idx_roundups_user_id ON roundups(user_id);
CREATE INDEX idx_roundups_allocated ON roundups(allocated);

CREATE TABLE IF NOT EXISTS roundup_batches (
                                 id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                 batch_date date NOT NULL,
                                 total_amount numeric(18,2) NOT NULL DEFAULT 0.00,
                                 created_at timestamptz NOT NULL DEFAULT now(),
                                 processed_by TEXT
);

CREATE UNIQUE INDEX ux_batch_date ON roundup_batches(batch_date);

CREATE TABLE IF NOT EXISTS portfolio_entries (
                                   id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                   user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                                   batch_id UUID NOT NULL REFERENCES roundup_batches(id) ON DELETE SET NULL,
                                   principal numeric(18,2) NOT NULL CHECK (principal >= 0.00),
                                   interest_accrued numeric(18,2) NOT NULL DEFAULT 0.00,
                                   effective_date timestamptz NOT NULL DEFAULT now(),
                                   created_at timestamptz NOT NULL DEFAULT now()
);

CREATE INDEX idx_portfolio_entries_user_id ON portfolio_entries(user_id);

CREATE TABLE IF NOT EXISTS user_portfolios (
                                 id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                 user_id UUID NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
                                 balance numeric(18,2) NOT NULL DEFAULT 0.00,
                                 last_updated timestamptz NOT NULL DEFAULT now()
);
CREATE INDEX idx_user_portfolios_user_id ON user_portfolios(user_id);
