-- V002 - Criação da tabela de CATEGORIES

CREATE TABLE categories (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(20) NOT NULL CHECK (type IN ('default','custom')),
    is_active BOOLEAN DEFAULT true
);

-- Índices
CREATE INDEX idx_categories_name ON categories(name);

-- Comentários
COMMENT ON TABLE categories IS 'Categorias de transações (padrão do sistema ou customizadas)';
COMMENT ON COLUMN categories.id IS 'Identificador único da categoria';
COMMENT ON COLUMN categories.name IS 'Nome da categoria';
COMMENT ON COLUMN categories.type IS 'Indica se é padrão ou customizada';
COMMENT ON COLUMN categories.is_active IS 'Indica se a categoria está ativa';