-- V003 - Criação da tabela de transações financeiras

CREATE TABLE transactions (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL REFERENCES users(id),
    category_id VARCHAR(255) REFERENCES categories(id),
    type VARCHAR(20) NOT NULL CHECK (type IN ('income','expense')),
    source VARCHAR(20) CHECK (source IN ('pdf','image','csv','xls','bank_sync')),
    status VARCHAR(20) DEFAULT 'pending' CHECK (status IN ('pending','confirmed','not_recognized','error_ocr')),
    amount NUMERIC(12,2) NOT NULL,
    description TEXT,
    transaction_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índices
CREATE INDEX idx_transactions_user_id ON transactions(user_id);
CREATE INDEX idx_transactions_category_id ON transactions(category_id);
CREATE INDEX idx_transactions_date ON transactions(transaction_date);

-- Comentários
COMMENT ON TABLE transactions IS 'Transações financeiras (receitas e despesas) associadas ao usuário';
COMMENT ON COLUMN transactions.id IS 'Identificador único da transação';
COMMENT ON COLUMN transactions.user_id IS 'Referência ao usuário dono da transação';
COMMENT ON COLUMN transactions.category_id IS 'Categoria associada à transação';
COMMENT ON COLUMN transactions.type IS 'Tipo: income (receita) ou expense (despesa)';
COMMENT ON COLUMN transactions.source IS 'Origem: pdf, image, csv, xls, bank_sync';
COMMENT ON COLUMN transactions.status IS 'Status do processamento OCR/NLP';
COMMENT ON COLUMN transactions.amount IS 'Valor da transação';
COMMENT ON COLUMN transactions.description IS 'Descrição detalhada da transação';
COMMENT ON COLUMN transactions.transaction_date IS 'Data efetiva da transação';
COMMENT ON COLUMN transactions.created_at IS 'Data/hora em que a transação foi registrada';
