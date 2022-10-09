CREATE TABLE IF NOT EXISTS "user_payment_card"
(
    id            BIGSERIAL PRIMARY KEY,
    created_at    TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at    TIMESTAMP    NOT NULL DEFAULT now(),
    version       INT          NOT NULL,
    card_number   TEXT         NOT NULL,
    expiring_date VARCHAR(5)   NOT NULL,
    cvv           INT          NOT NULL,
    custom_name  VARCHAR(255) NOT NULL,
    user_id       BIGINT       NOT NULL,

    CONSTRAINT fk_user_payment_method_user_id
        FOREIGN KEY (user_id)
            REFERENCES "user" (id)
);

CREATE INDEX IF NOT EXISTS fk_user_payment_method_user_idx ON user_payment_card (user_id);