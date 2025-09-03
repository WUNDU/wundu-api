-- V006 - Criação da tabela de histórico do chatbot

CREATE TABLE chatbot_history (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL REFERENCES users(id),
    message TEXT NOT NULL,
    sender VARCHAR(10) NOT NULL CHECK (sender IN ('user','bot')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índices
CREATE INDEX idx_chatbot_user_id ON chatbot_history(user_id);
CREATE INDEX idx_chatbot_sender ON chatbot_history(sender);

-- Comentários
COMMENT ON TABLE chatbot_history IS 'Histórico de interações do usuário com o chatbot';
COMMENT ON COLUMN chatbot_history.id IS 'Identificador único da interação';
COMMENT ON COLUMN chatbot_history.user_id IS 'Usuário associado à interação';
COMMENT ON COLUMN chatbot_history.message IS 'Conteúdo da mensagem trocada';
COMMENT ON COLUMN chatbot_history.sender IS 'Origem da mensagem: user ou bot';
COMMENT ON COLUMN chatbot_history.created_at IS 'Data/hora em que a mensagem foi registrada';
