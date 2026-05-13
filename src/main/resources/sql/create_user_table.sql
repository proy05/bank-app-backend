CREATE TABLE bank_app.user (
                       id BIGSERIAL PRIMARY KEY,
                       first_name VARCHAR(100) NOT NULL,
                       last_name VARCHAR(100) NOT NULL,
                       gender VARCHAR(10) NOT NULL,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       pan_number CHAR(10) NOT NULL UNIQUE,
                       date_of_birth DATE NOT NULL,
                        -- Audit fields:
                       created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);