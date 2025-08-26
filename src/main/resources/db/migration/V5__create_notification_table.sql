CREATE TABLE notifications (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    type VARCHAR(50),
    title TEXT NOT NULL,
    message TEXT NOT NULL,
    is_read BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_notification_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE INDEX idx_notifications_user_id ON notifications(user_id);
CREATE INDEX idx_notifications_is_read ON notifications(is_read);

COMMENT ON TABLE notifications IS 'Notificações enviadas aos usuários sobre finanças, alertas e dicas';
COMMENT ON COLUMN notifications.type IS 'Tipo da notificação: alerta de orçamento, dica, lembrete de meta, etc.';
COMMENT ON COLUMN notifications.is_read IS 'Indica se o usuário já leu a notificação';
