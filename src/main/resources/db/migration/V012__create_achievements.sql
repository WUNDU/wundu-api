-- V010 - Criação da tabela de conquistas e relação com usuários

CREATE TABLE achievements (
    id VARCHAR(255) PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL
);

-- Comentários
COMMENT ON TABLE achievements IS 'Conquistas/gamificação liberadas dentro da Wundu';
COMMENT ON COLUMN achievements.id IS 'Identificador único da conquista';
COMMENT ON COLUMN achievements.code IS 'Código único da conquista';
COMMENT ON COLUMN achievements.name IS 'Nome da conquista';
COMMENT ON COLUMN achievements.description IS 'Descrição da conquista';


CREATE TABLE user_achievements (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL REFERENCES users(id),
    achievement_id VARCHAR(255) NOT NULL REFERENCES achievements(id),
    unlocked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, achievement_id)
);

-- Índices
CREATE INDEX idx_user_achievements_user ON user_achievements(user_id);
CREATE INDEX idx_user_achievements_achievement ON user_achievements(achievement_id);

-- Comentários
COMMENT ON TABLE user_achievements IS 'Tabela de associação entre usuários e conquistas desbloqueadas';
COMMENT ON COLUMN user_achievements.id IS 'Identificador único da relação';
COMMENT ON COLUMN user_achievements.user_id IS 'Usuário que desbloqueou a conquista';
COMMENT ON COLUMN user_achievements.achievement_id IS 'Conquista desbloqueada';
COMMENT ON COLUMN user_achievements.unlocked_at IS 'Data/hora do desbloqueio';