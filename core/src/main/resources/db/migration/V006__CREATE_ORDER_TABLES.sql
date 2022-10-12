CREATE TABLE IF NOT EXISTS "order"
(
    id                     BIGSERIAL PRIMARY KEY,
    created_at             TIMESTAMP NOT NULL DEFAULT now(),
    updated_at             TIMESTAMP NOT NULL DEFAULT now(),
    version                INT       NOT NULL,
    user_payment_card_id   INT       NOT NULL,
    user_id                INT       NOT NULL,

    CONSTRAINT fk_order_user_payment_method_id
        FOREIGN KEY (user_payment_card_id)
            REFERENCES "user_payment_card" (id),
    CONSTRAINT fk_order_user_id
        FOREIGN KEY (user_id)
            REFERENCES "user" (id)
);

CREATE INDEX IF NOT EXISTS fk_order_user_payment_method_idx ON "order" (user_payment_card_id);

CREATE TABLE IF NOT EXISTS "order_status"
(
    id         BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at TIMESTAMP    NOT NULL DEFAULT now(),
    version    INT          NOT NULL,
    order_id   INT          NOT NULL,
    status     VARCHAR(255) NOT NULL,
    active    BOOLEAN      NOT NULL,

    CONSTRAINT fk_order_status_order_id
        FOREIGN KEY (order_id)
            REFERENCES "order" (id)
);

CREATE INDEX IF NOT EXISTS fk_order_status_order_idx ON order_status (order_id);

CREATE TABLE IF NOT EXISTS "order_product"
(
    product_id  BIGINT  NOT NULL,
    order_id    INT     NOT NULL,
    quantity    INT     NOT NULL,
    total_price DECIMAL NOT NULL,
    PRIMARY KEY (product_id, order_id),
    CONSTRAINT fk_product_has_order_product_id
        FOREIGN KEY (product_id)
            REFERENCES "product" (id),
    CONSTRAINT fk_product_has_order_order_id
        FOREIGN KEY (order_id)
            REFERENCES "order" (id)
);

CREATE INDEX IF NOT EXISTS fk_product_has_order_order_idx ON order_product (order_id);
CREATE INDEX IF NOT EXISTS fk_product_has_order_product_idx ON order_product (product_id);