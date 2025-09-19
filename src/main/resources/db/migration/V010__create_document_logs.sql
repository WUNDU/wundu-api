-- V010 - Criação da tabela DOCUMENT_LOGS e proteção de imutabilidade

CREATE TABLE IF NOT EXISTS document_logs (
    id VARCHAR(255) PRIMARY KEY,
    actor_id VARCHAR(255) NOT NULL,     -- usuário que realizou o upload
    file_name VARCHAR(1024) NOT NULL,   -- nome do ficheiro enviado
    content_type VARCHAR(255) NOT NULL, -- tipo MIME (application/pdf, image/png, ...)
    file_size BIGINT NOT NULL,          -- tamanho em bytes
    type VARCHAR(50) NOT NULL,          -- DOCUMENT_UPLOAD | DOCUMENT_UPLOAD_ERROR
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_document_actor FOREIGN KEY (actor_id) REFERENCES users(id)
);

ALTER TABLE document_logs
    ADD CONSTRAINT chk_document_log_type CHECK (type IN ('DOCUMENT_UPLOAD', 'DOCUMENT_UPLOAD_ERROR'));

CREATE INDEX IF NOT EXISTS idx_document_actor ON document_logs(actor_id);
CREATE INDEX IF NOT EXISTS idx_document_type ON document_logs(type);
CREATE INDEX IF NOT EXISTS idx_document_created_at ON document_logs(created_at);


COMMENT ON TABLE document_logs IS 'Logs de upload de documentos (sucesso e erro), estruturados em JSON/colunas';
COMMENT ON COLUMN document_logs.actor_id IS 'Usuário que realizou o upload';
COMMENT ON COLUMN document_logs.file_name IS 'Nome do ficheiro enviado';
COMMENT ON COLUMN document_logs.content_type IS 'Tipo MIME do ficheiro';
COMMENT ON COLUMN document_logs.file_size IS 'Tamanho do ficheiro em bytes';
COMMENT ON COLUMN document_logs.type IS 'Tipo do log: DOCUMENT_UPLOAD ou DOCUMENT_UPLOAD_ERROR';
COMMENT ON COLUMN document_logs.created_at IS 'Momento em que o evento foi registrado';
