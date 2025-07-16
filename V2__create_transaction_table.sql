CREATE TABLE transactions (
    id VARCHAR(255) PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    source VARCHAR(50),
    amount NUMERIC(12, 2) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    description TEXT,
    transaction_date DATE NOT NULL,
    date_time TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_transaction_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE INDEX idx_transactions_user_id ON transactions(user_id);
CREATE INDEX idx_transactions_type ON transactions(type);
CREATE INDEX idx_transactions_date_time ON transactions(date_time);
CREATE INDEX idx_transactions_transaction_date ON transactions(transaction_date);

COMMENT ON TABLE transactions IS 'Tabela de transações financeiras (receitas e despesas)';
COMMENT ON COLUMN transactions.id IS 'Identificador único da transação';
COMMENT ON COLUMN transactions.type IS 'Tipo da transação (ex: income, expense)';
COMMENT ON COLUMN transactions.source IS 'Fonte da transação (ex: PDF, imagem, manual)';
COMMENT ON COLUMN transactions.amount IS 'Valor da transação';
COMMENT ON COLUMN transactions.user_id IS 'Referência ao usuário proprietário da transação';
COMMENT ON COLUMN transactions.description IS 'Descrição opcional da transação';
COMMENT ON COLUMN transactions.transaction_date IS 'Data real do movimento (extraído de fatura ou comprovativo)';
COMMENT ON COLUMN transactions.date_time IS 'Data e hora da criação da transação na plataforma';
