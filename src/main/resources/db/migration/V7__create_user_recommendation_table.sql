CREATE TABLE user_recommendations (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    content_type VARCHAR(50) NOT NULL,
    content_id VARCHAR(255) NOT NULL,
    recommendation_reason TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_user_recommendations_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE INDEX idx_recommendations_user_id ON user_recommendations(user_id);
CREATE INDEX idx_recommendations_content ON user_recommendations(content_type, content_id);

COMMENT ON TABLE user_recommendations IS 'Recomendações de conteúdos personalizadas feitas para os usuários';
COMMENT ON COLUMN user_recommendations.content_type IS 'Tipo de conteúdo recomendado (ex: artigo, vídeo, produto, etc)';
COMMENT ON COLUMN user_recommendations.content_id IS 'Identificador do conteúdo recomendado';
COMMENT ON COLUMN user_recommendations.recommendation_reason IS 'Justificativa ou motivo da recomendação (ex: baseado em interesses, comportamento, etc)';
COMMENT ON COLUMN user_recommendations.created_at IS 'Data e hora em que a recomendação foi gerada';
