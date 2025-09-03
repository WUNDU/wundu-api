-- V009 - Criação da tabela de registros OCR

CREATE TABLE ocr_records (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL REFERENCES users(id),
    file_name VARCHAR(255) NOT NULL,
    content_type VARCHAR(100) NOT NULL,
    file_size BIGINT NOT NULL,
    extracted_text TEXT,
    status VARCHAR(20) DEFAULT 'pending' CHECK (status IN ('pending','processed','error')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índices
CREATE INDEX idx_ocr_user ON ocr_records(user_id);
CREATE INDEX idx_ocr_status ON ocr_records(status);
CREATE INDEX idx_ocr_created_at ON ocr_records(created_at);

-- Comentários
COMMENT ON TABLE ocr_records IS 'Registros de documentos processados por OCR';
COMMENT ON COLUMN ocr_records.id IS 'Identificador único do registro OCR';
COMMENT ON COLUMN ocr_records.user_id IS 'Usuário que fez upload do arquivo';
COMMENT ON COLUMN ocr_records.file_name IS 'Nome do arquivo enviado';
COMMENT ON COLUMN ocr_records.content_type IS 'Tipo de conteúdo (MIME type)';
COMMENT ON COLUMN ocr_records.file_size IS 'Tamanho do arquivo em bytes';
COMMENT ON COLUMN ocr_records.extracted_text IS 'Texto extraído do arquivo após OCR';
COMMENT ON COLUMN ocr_records.status IS 'Status do processamento do arquivo';
COMMENT ON COLUMN ocr_records.created_at IS 'Data/hora de criação do registro';
