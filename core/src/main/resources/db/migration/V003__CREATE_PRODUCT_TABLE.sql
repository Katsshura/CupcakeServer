CREATE TABLE IF NOT EXISTS "product"
(
    id				BIGSERIAL PRIMARY KEY,
    created_at 		TIMESTAMP NOT NULL DEFAULT now(),
    updated_at 		TIMESTAMP NOT NULL DEFAULT now(),
    version			INT NOT NULL,
    name			VARCHAR(255) NOT NULL,
    description		TEXT NOT NULL,
    price			NUMERIC NOT NULL,
    image_url		TEXT NOT NULL,
    available_stock	BIGINT NOT NULL DEFAULT 0,
    popularity		NUMERIC NOT NULL DEFAULT 0.0
);