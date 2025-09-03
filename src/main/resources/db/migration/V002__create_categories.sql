-- V002 - Criação da tabela de categorias

CREATE TABLE categories (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(20) NOT NULL CHECK (type IN ('default','custom'))
);

-- Índices
CREATE INDEX idx_categories_name ON categories(name);

-- Comentários
COMMENT ON TABLE categories IS 'Categorias de transações (padrão do sistema ou personalizadas pelo usuário)';
COMMENT ON COLUMN categories.id IS 'Identificador único da categoria';
COMMENT ON COLUMN categories.name IS 'Nome da categoria (ex: Alimentação, Transporte)';
COMMENT ON COLUMN categories.type IS 'Define se é uma categoria padrão (default) ou personalizada (custom)';