-- Criação da tabela de notification no PostgreSQL
CREATE TABLE notification (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(100) NOT NULL,
    title VARCHAR(100) NOT NULL,
    message Text,
    create_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    is_read BOOLEAN DEFAULT false,
    CONSTRAINT fk_notification_user FOREIGN KEY (user_id) REFERENCES users(id)
);