-- 3. TABELA DE LIMITES POR USUÁRIO E CATEGORIA
CREATE TABLE user_category_limit (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    category_id VARCHAR(255) NOT NULL,
    monthly_limit DOUBLE PRECISION NOT NULL DEFAULT -1,

    CONSTRAINT fk_user_category_limit_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_user_category_limit_category FOREIGN KEY (category_id) REFERENCES categories(id),
    CONSTRAINT uk_user_category UNIQUE (user_id, category_id)
);

COMMENT ON TABLE user_category_limit IS 'Limites mensais definidos por usuário para categorias específicas';

COMMENT ON COLUMN user_category_limit.id IS 'Identificador único do limite por usuário e categoria';
COMMENT ON COLUMN user_category_limit.user_id IS 'Referência ao usuário';
COMMENT ON COLUMN user_category_limit.category_id IS 'Referência à categoria';
COMMENT ON COLUMN user_category_limit.monthly_limit IS 'Limite mensal definido pelo usuário para esta categoria';

-- Índices úteis para desempenho de busca
CREATE INDEX idx_user_category_limit_user_id ON user_category_limit(user_id);
CREATE INDEX idx_user_category_limit_category_id ON user_category_limit(category_id);
