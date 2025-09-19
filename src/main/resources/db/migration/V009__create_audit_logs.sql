-- V009 - Criação da tabela de AUDIT_LOGS

CREATE TABLE audit_logs (
    id VARCHAR(255) PRIMARY KEY,
    actor_id VARCHAR(255) NOT NULL,              -- Usuário que realizou a ação
    target_user_id VARCHAR(255),                 -- Usuário afetado
    action VARCHAR(50) NOT NULL,                 -- create, update, deactivate, reactivate
    changed_fields JSONB,                        -- Campos alterados em formato JSON
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_audit_actor FOREIGN KEY (actor_id) REFERENCES users(id),
    CONSTRAINT fk_audit_target FOREIGN KEY (target_user_id) REFERENCES users(id)
);

-- Índices para consultas mais rápidas
CREATE INDEX idx_audit_actor ON audit_logs(actor_id);
CREATE INDEX idx_audit_target ON audit_logs(target_user_id);
CREATE INDEX idx_audit_action ON audit_logs(action);
CREATE INDEX idx_audit_created_at ON audit_logs(created_at);

-- Comentários
COMMENT ON TABLE audit_logs IS 'Tabela de auditoria de ações realizadas no sistema';
COMMENT ON COLUMN audit_logs.actor_id IS 'Usuário que realizou a ação';
COMMENT ON COLUMN audit_logs.target_user_id IS 'Usuário afetado pela ação';
COMMENT ON COLUMN audit_logs.action IS 'Tipo da ação (create, update, deactivate, reactivate)';
COMMENT ON COLUMN audit_logs.changed_fields IS 'Campos alterados em formato JSON';
COMMENT ON COLUMN audit_logs.created_at IS 'Data/hora em que a ação foi registrada';
