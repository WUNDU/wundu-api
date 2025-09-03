-- V005 - Criação da tabela de notificações

CREATE TABLE notifications (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL REFERENCES users(id),
    notif_type VARCHAR(20) NOT NULL CHECK (notif_type IN ('simple','intelligent')),
    priority VARCHAR(20) DEFAULT 'low' CHECK (priority IN ('low','medium','high')),
    title TEXT NOT NULL,
    message TEXT NOT NULL,
    is_read BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índices
CREATE INDEX idx_notifications_user_id ON notifications(user_id);
CREATE INDEX idx_notifications_is_read ON notifications(is_read);
CREATE INDEX idx_notifications_priority ON notifications(priority);

-- Comentários
COMMENT ON TABLE notifications IS 'Notificações enviadas aos usuários (alertas, dicas, lembretes)';
COMMENT ON COLUMN notifications.id IS 'Identificador único da notificação';
COMMENT ON COLUMN notifications.user_id IS 'Usuário destinatário da notificação';
COMMENT ON COLUMN notifications.notif_type IS 'Tipo da notificação: simple ou intelligent';
COMMENT ON COLUMN notifications.priority IS 'Prioridade da notificação: low, medium, high';
COMMENT ON COLUMN notifications.title IS 'Título/resumo da notificação';
COMMENT ON COLUMN notifications.message IS 'Mensagem detalhada da notificação';
COMMENT ON COLUMN notifications.is_read IS 'Indica se a notificação foi lida pelo usuário';
COMMENT ON COLUMN notifications.created_at IS 'Data/hora de criação da notificação';
