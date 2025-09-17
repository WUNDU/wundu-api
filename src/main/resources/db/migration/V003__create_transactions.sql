-- V003 - Criação da tabela de TRANSACTIONS

-- Transações
CREATE TABLE transactions (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    category_id VARCHAR(255),
    type VARCHAR(25) NOT NULL, -- income / expense
    source VARCHAR(50),        -- pdf, image, csv, etc
    status VARCHAR(25) DEFAULT 'pending',
    amount NUMERIC(12,2) NOT NULL,
    description TEXT,
    transaction_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_transaction_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_transaction_category FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Índices básicos
CREATE INDEX idx_transactions_user_id ON transactions(user_id);
CREATE INDEX idx_transactions_category_id ON transactions(category_id);
CREATE INDEX idx_transactions_date ON transactions(transaction_date);
CREATE INDEX idx_transactions_status ON transactions(status);

-- Índice composto (para melhorar filtros combinados de user_id + status + date)
CREATE INDEX idx_transactions_user_status_date
    ON transactions(user_id, status, transaction_date);

-- Comentários
COMMENT ON TABLE transactions IS 'Tabela de transações financeiras (OCR ou sincronização)';
COMMENT ON COLUMN transactions.id IS 'Identificador único da transação';
COMMENT ON COLUMN transactions.user_id IS 'Usuário dono da transação';
COMMENT ON COLUMN transactions.category_id IS 'Categoria associada';
COMMENT ON COLUMN transactions.type IS 'Tipo: income (receita) ou expense (despesa)';
COMMENT ON COLUMN transactions.source IS 'Origem: pdf, image, csv, xls, bank_sync';
COMMENT ON COLUMN transactions.status IS 'Status da transação: pending, confirmed, not_recognized';
COMMENT ON COLUMN transactions.amount IS 'Valor da transação';
COMMENT ON COLUMN transactions.description IS 'Descrição da transação';
COMMENT ON COLUMN transactions.transaction_date IS 'Data real da transação';
COMMENT ON COLUMN transactions.created_at IS 'Data/hora da criação';
