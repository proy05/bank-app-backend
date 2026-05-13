CREATE TABLE bank_app.transaction (
                                       id BIGSERIAL PRIMARY KEY,
                                       from_account_number VARCHAR(12) NOT NULL,
                                       to_account_number VARCHAR(12) NOT NULL,
                                       amount NUMERIC(15, 2) NOT NULL CHECK (amount > 0),
                                       timestamp TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                       description VARCHAR(255),

                                       CONSTRAINT fk_from_account
                                           FOREIGN KEY (from_account_number)
                                               REFERENCES bank_app.account(account_number),

                                       CONSTRAINT fk_to_account
                                           FOREIGN KEY (to_account_number)
                                               REFERENCES bank_app.account(account_number),

    -- Prevent transferring to the same account at the DB level
                                       CONSTRAINT chk_different_accounts
                                           CHECK (from_account_number <> to_account_number)
);

-- Indices are CRITICAL for generating monthly statements quickly
CREATE INDEX idx_transaction_from_acc ON bank_app.transaction(from_account_number);
CREATE INDEX idx_transaction_to_acc ON bank_app.transaction(to_account_number);
CREATE INDEX idx_transaction_timestamp ON bank_app.transaction(timestamp);