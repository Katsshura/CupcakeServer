CREATE TABLE IF NOT EXISTS "user"
(
    id				BIGSERIAL PRIMARY KEY,
    created_at 		TIMESTAMP NOT NULL DEFAULT now(),
    updated_at 		TIMESTAMP NOT NULL DEFAULT now(),
    version			INT NOT NULL,
    email			VARCHAR(255) NOT NULL UNIQUE,
    name			VARCHAR(255) NOT NULL,
    cpf 			VARCHAR(11) NOT NULL UNIQUE,
    birthday_date	DATE NOT NULL,
    password		TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS "address"
(
    id				BIGSERIAL PRIMARY KEY,
    created_at 		TIMESTAMP NOT NULL DEFAULT now(),
    updated_at 		TIMESTAMP NOT NULL DEFAULT now(),
    version			INT NOT NULL,
    cep				VARCHAR(8) NOT NULL,
    logradouro		VARCHAR(255) NOT NULL,
    property_number VARCHAR(30) NOT NULL,
    complement		VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS "user_address"
(
    user_id 		BIGSERIAL NOT NULL,
    address_id 	BIGSERIAL NOT NULL,
    PRIMARY KEY (user_id, address_id),
    CONSTRAINT fk_user_has_address_user_id
        FOREIGN KEY (User_id)
            REFERENCES "user" (id),
    CONSTRAINT fk_user_has_address_address_id
        FOREIGN KEY (address_id)
            REFERENCES "address" (id)
);

CREATE INDEX IF NOT EXISTS fk_user_has_address_address_idx ON user_address (address_id);
CREATE INDEX IF NOT EXISTS fk_user_has_address_user_idx ON user_address (user_id);