ALTER TABLE user_payment_card ADD COLUMN IF NOT EXISTS card_flag VARCHAR(255) NOT NULL DEFAULT 'UNDEFINED';