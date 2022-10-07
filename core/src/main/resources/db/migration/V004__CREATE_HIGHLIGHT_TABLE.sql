CREATE TABLE IF NOT EXISTS "highlights"
(
    id				BIGSERIAL PRIMARY KEY,
    created_at 		TIMESTAMP NOT NULL DEFAULT now(),
    updated_at 		TIMESTAMP NOT NULL DEFAULT now(),
    version			INT NOT NULL,
    title           TEXT NOT NULL,
    subtitle 		TEXT NOT NULL,
    product_id 		BIGINT NOT NULL,
    enabled         BOOLEAN NOT NULL DEFAULT true,
    CONSTRAINT fk_highlights_product_id
        FOREIGN KEY (product_id)
            REFERENCES "product" (id)
);

CREATE INDEX IF NOT EXISTS fk_highlights_product_idx ON highlights (product_id);