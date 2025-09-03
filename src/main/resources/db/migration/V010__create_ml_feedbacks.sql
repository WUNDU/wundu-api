-- V008 - Criação da tabela de feedback de machine learning

CREATE TABLE ml_feedbacks (
    id VARCHAR(255) PRIMARY KEY,
    transaction_id VARCHAR(255) NOT NULL REFERENCES transactions(id),
    old_category_id VARCHAR(255),
    new_category_id VARCHAR(255),
    user_id VARCHAR(255) NOT NULL REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índices
CREATE INDEX idx_ml_feedbacks_transaction ON ml_feedbacks(transaction_id);
CREATE INDEX idx_ml_feedbacks_user ON ml_feedbacks(user_id);

-- Comentários
COMMENT ON TABLE ml_feedbacks IS 'Feedback dos usuários para ajustar e treinar modelos de categorização';
COMMENT ON COLUMN ml_feedbacks.id IS 'Identificador único do feedback';
COMMENT ON COLUMN ml_feedbacks.transaction_id IS 'Transação alvo do feedback';
COMMENT ON COLUMN ml_feedbacks.old_category_id IS 'Categoria sugerida originalmente';
COMMENT ON COLUMN ml_feedbacks.new_category_id IS 'Categoria corrigida pelo usuário';
COMMENT ON COLUMN ml_feedbacks.user_id IS 'Usuário que forneceu o feedback';
COMMENT ON COLUMN ml_feedbacks.created_at IS 'Data/hora do feedback';
