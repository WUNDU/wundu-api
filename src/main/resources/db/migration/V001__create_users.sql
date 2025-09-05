-- V002 - Criação da tabela de USERS

-- Tabela de usuários
CREATE TABLE users (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(200) NOT NULL,
    phone_number VARCHAR(20) NOT NULL UNIQUE,
    role VARCHAR(50) NOT NULL CHECK (role IN ('ADMIN','CLIENTE')) DEFAULT 'CLIENTE',
    plan_type VARCHAR(20) NOT NULL CHECK (plan_type IN ('FREE','PREMIUM')) DEFAULT 'FREE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP,
    created_by VARCHAR(255),
    modified_by VARCHAR(255),
    is_active BOOLEAN DEFAULT true
);

-- Índices para performance
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_phone_number ON users(phone_number);
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_users_plan_type ON users(plan_type);
CREATE INDEX idx_users_created_at ON users(created_at);
CREATE INDEX idx_users_is_active ON users(is_active);

-- Trigger para atualizar updated_at
CREATE OR REPLACE FUNCTION update_users_updated_at()
RETURNS TRIGGER AS $$
BEGIN
   NEW.updated_at = CURRENT_TIMESTAMP;
   RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_users_updated_at
BEFORE UPDATE ON users
FOR EACH ROW EXECUTE FUNCTION update_users_updated_at();

-- Comentários
COMMENT ON TABLE users IS 'Tabela de usuários do sistema Wundu';
COMMENT ON COLUMN users.id IS 'Identificador único do usuário';
COMMENT ON COLUMN users.name IS 'Nome completo do usuário';
COMMENT ON COLUMN users.email IS 'Email único do usuário';
COMMENT ON COLUMN users.password IS 'Senha criptografada do usuário';
COMMENT ON COLUMN users.phone_number IS 'Número de telefone do usuário';
COMMENT ON COLUMN users.role IS 'Papel do usuário no sistema (admin, user)';
COMMENT ON COLUMN users.plan_type IS 'Plano do usuário (Free ou Premium)';
COMMENT ON COLUMN users.created_at IS 'Data e hora de criação';
COMMENT ON COLUMN users.updated_at IS 'Data e hora da última atualização';
COMMENT ON COLUMN users.last_login IS 'Último login do usuário';
COMMENT ON COLUMN users.created_by IS 'Usuário que criou o registro';
COMMENT ON COLUMN users.modified_by IS 'Usuário que modificou pela última vez';
COMMENT ON COLUMN users.is_active IS 'Status de atividade do usuário';
