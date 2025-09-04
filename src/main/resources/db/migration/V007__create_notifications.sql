-- V007 - Criação da tabela de NOTIFICATIONS

CREATE TABLE notifications (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    type VARCHAR(25) NOT NULL DEFAULT 'simple', -- simple/intelligent
    title TEXT NOT NULL,
    message TEXT NOT NULL,
    is_read BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_notification_user FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Índices
CREATE INDEX idx_notifications_user_id ON notifications(user_id);
CREATE INDEX idx_notifications_is_read ON notifications(is_read);

-- Comentários
COMMENT ON TABLE notifications IS 'Notificações enviadas aos usuários';
COMMENT ON COLUMN notifications.id IS 'Identificador único da notificação';
COMMENT ON COLUMN notifications.user_id IS 'Usuário destinatário';
COMMENT ON COLUMN notifications.type IS 'Tipo: simples ou inteligente';
COMMENT ON COLUMN notifications.title IS 'Título da notificação';
COMMENT ON COLUMN notifications.message IS 'Mensagem detalhada';
COMMENT ON COLUMN notifications.is_read IS 'Status de leitura';
COMMENT ON COLUMN notifications.created_at IS 'Data/hora de criação da notificação';

