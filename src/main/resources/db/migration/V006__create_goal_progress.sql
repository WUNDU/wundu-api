-- V006 - Criação da tabela de GOAL PROGRESS

-- Progresso da meta
CREATE TABLE goal_progress (
    id VARCHAR(255) PRIMARY KEY,
    goal_id VARCHAR(255) NOT NULL,
    amount NUMERIC(12,2) NOT NULL,
    progress_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_goal_progress FOREIGN KEY (goal_id) REFERENCES financial_goals(id)
);

-- Índice para acelerar consultas por meta e data
CREATE INDEX idx_goal_progress_goal_date ON goal_progress(goal_id, progress_date);

-- Comentários
COMMENT ON TABLE goal_progress IS 'Histórico da evolução de metas financeiras';
COMMENT ON COLUMN goal_progress.id IS 'Identificador único do progresso';
COMMENT ON COLUMN goal_progress.goal_id IS 'Meta associada';
COMMENT ON COLUMN goal_progress.amount IS 'Valor registrado no progresso da meta';
COMMENT ON COLUMN goal_progress.progress_date IS 'Data de referência do progresso';
COMMENT ON COLUMN goal_progress.created_at IS 'Data/hora do registro do progresso';
