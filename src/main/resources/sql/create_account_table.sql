CREATE TABLE bank_app.account (
                         id BIGSERIAL PRIMARY KEY,
                         account_number VARCHAR(12) NOT NULL UNIQUE, --varchar becoz there can be leading zeros
                         account_type VARCHAR(10) NOT NULL,
                         balance NUMERIC(15, 2) NOT NULL,
                         user_id BIGINT,
                         CONSTRAINT fk_user
                         FOREIGN KEY(user_id)
                         REFERENCES bank_app.user(id)
                         ON DELETE NO ACTION --cascade delete managed by application
);