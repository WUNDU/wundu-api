-- V004 - Criação da tabela de metas financeiras

CREATE TABLE financial_goals (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL REFERENCES users(id),
    category_id VARCHAR(255) REFERENCES categories(id),
    title VARCHAR(150) NOT NULL,
    description TEXT,
    goal_type VARCHAR(20) NOT NULL CHECK (goal_type IN ('short_term','long_term')),
    target_amount NUMERIC(12,2) NOT NULL,
    current_amount NUMERIC(12,2) DEFAULT 0,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'active' CHECK (status IN ('active','at_risk','done')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índices
CREATE INDEX idx_goals_user_id ON financial_goals(user_id);
CREATE INDEX idx_goals_status ON financial_goals(status);

-- Comentários
COMMENT ON TABLE financial_goals IS 'Metas financeiras definidas pelos usuários (curto ou longo prazo)';
COMMENT ON COLUMN financial_goals.id IS 'Identificador único da meta';
COMMENT ON COLUMN financial_goals.user_id IS 'Usuário dono da meta';
COMMENT ON COLUMN financial_goals.category_id IS 'Categoria relacionada à meta';
COMMENT ON COLUMN financial_goals.title IS 'Título/nome da meta';
COMMENT ON COLUMN financial_goals.description IS 'Descrição detalhada da meta';
COMMENT ON COLUMN financial_goals.goal_type IS 'short_term (até 12 meses) ou long_term (mais de 12 meses)';
COMMENT ON COLUMN financial_goals.target_amount IS 'Valor objetivo a ser atingido';
COMMENT ON COLUMN financial_goals.current_amount IS 'Quanto já foi acumulado para esta meta';
COMMENT ON COLUMN financial_goals.start_date IS 'Data de início da meta';
COMMENT ON COLUMN financial_goals.end_date IS 'Prazo final para atingir a meta';
COMMENT ON COLUMN financial_goals.status IS 'Status atual: active, at_risk ou done';
COMMENT ON COLUMN financial_goals.created_at IS 'Data/hora de criação da meta';
