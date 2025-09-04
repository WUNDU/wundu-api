-- V006 - Criação da tabela de GOAL PROGRESS

-- Progresso da meta
CREATE TABLE goal_progress (
    id VARCHAR(255) PRIMARY KEY,
    goal_id VARCHAR(255) NOT NULL,
    progress_percent NUMERIC(5,2) NOT NULL,
    accumulated_amount NUMERIC(12,2) NOT NULL,
    recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_goal_progress FOREIGN KEY (goal_id) REFERENCES financial_goals(id)
);

CREATE INDEX idx_goal_progress_goal_id ON goal_progress(goal_id);

COMMENT ON TABLE goal_progress IS 'Histórico da evolução de metas financeiras';
COMMENT ON COLUMN goal_progress.id IS 'Identificador único do progresso';
COMMENT ON COLUMN goal_progress.goal_id IS 'Meta associada';
COMMENT ON COLUMN goal_progress.progress_percent IS 'Progresso em %';
COMMENT ON COLUMN goal_progress.accumulated_amount IS 'Valor acumulado até a data';
COMMENT ON COLUMN goal_progress.recorded_at IS 'Data/hora do registro do progresso';
