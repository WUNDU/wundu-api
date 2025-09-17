-- V005 - Criação da tabela de FINANCIAL GOALS

-- Metas financeiras
CREATE TABLE financial_goals (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    title VARCHAR(150) NOT NULL,
    description TEXT,
    type VARCHAR(25) NOT NULL, -- short_term / long_term
    target_amount NUMERIC(12,2) NOT NULL,
    current_amount NUMERIC(12,2) DEFAULT 0,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status VARCHAR(25) DEFAULT 'active', -- active, at_risk, done
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_goal_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE INDEX idx_goals_user_id ON financial_goals(user_id);
CREATE INDEX idx_goals_status ON financial_goals(status);

COMMENT ON TABLE financial_goals IS 'Metas financeiras criadas por usuários Premium';
COMMENT ON COLUMN financial_goals.id IS 'Identificador único da meta';
COMMENT ON COLUMN financial_goals.user_id IS 'Usuário dono da meta';
COMMENT ON COLUMN financial_goals.title IS 'Nome da meta (ex: Viagem, Reserva de emergência)';
COMMENT ON COLUMN financial_goals.description IS 'Descrição opcional';
COMMENT ON COLUMN financial_goals.type IS 'Tipo: curto ou longo prazo';
COMMENT ON COLUMN financial_goals.target_amount IS 'Valor alvo da meta';
COMMENT ON COLUMN financial_goals.current_amount IS 'Valor já acumulado';
COMMENT ON COLUMN financial_goals.start_date IS 'Data de início';
COMMENT ON COLUMN financial_goals.end_date IS 'Data final esperada';
COMMENT ON COLUMN financial_goals.status IS 'Status: active, at_risk, done';
COMMENT ON COLUMN financial_goals.created_at IS 'Data/hora de criação';
