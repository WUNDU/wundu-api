-- 1. TABELA DE CATEGORIAS
CREATE TABLE categories (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
);

COMMENT ON TABLE categories IS 'Tabela de categorias de transações (ex: Alimentação, Transporte)';
COMMENT ON COLUMN categories.id IS 'Identificador único da categoria';
COMMENT ON COLUMN categories.name IS 'Nome da categoria';

-- 2. ALTERAÇÃO DA TABELA TRANSACTIONS
ALTER TABLE transactions
ADD COLUMN category_id VARCHAR(255);

ALTER TABLE transactions
ADD CONSTRAINT fk_transaction_category FOREIGN KEY (category_id) REFERENCES categories(id);

CREATE INDEX idx_transactions_category_id ON transactions(category_id);

COMMENT ON COLUMN transactions.category_id IS 'Categoria associada à transação (pode ser nula inicialmente)';
