CREATE TABLE financial_goals (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    target_amount NUMERIC(12, 2) NOT NULL,
    current_amount NUMERIC(12, 2) DEFAULT 0,
    start_date DATE,
    end_date DATE,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_goal_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE INDEX idx_goals_user_id ON financial_goals(user_id);
CREATE INDEX idx_goals_is_active ON financial_goals(is_active);

COMMENT ON TABLE financial_goals IS 'Metas financeiras criadas pelos usuários';
COMMENT ON COLUMN financial_goals.target_amount IS 'Valor total que o usuário quer atingir';
COMMENT ON COLUMN financial_goals.current_amount IS 'Quanto já foi acumulado até agora (calculado ou somado manualmente)';
COMMENT ON COLUMN financial_goals.start_date IS 'Data de início da meta';
COMMENT ON COLUMN financial_goals.end_date IS 'Data final esperada para atingir a meta';
