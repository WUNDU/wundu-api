-- V004 - Criação da tabela de OCR RECORDS

CREATE TABLE ocr_records (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    content_type VARCHAR(100) NOT NULL,
    file_size BIGINT NOT NULL,
    extracted_text TEXT,
    status VARCHAR(25) DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_ocr_user FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Índices
CREATE INDEX idx_ocr_records_user_created_at 
    ON ocr_records(user_id, created_at);

-- Comentários
COMMENT ON TABLE ocr_records IS 'Histórico de documentos enviados para OCR';
COMMENT ON COLUMN ocr_records.id IS 'Identificador único do OCR record';
COMMENT ON COLUMN ocr_records.user_id IS 'Usuário que fez o upload';
COMMENT ON COLUMN ocr_records.file_name IS 'Nome original do arquivo';
COMMENT ON COLUMN ocr_records.content_type IS 'Formato/MIME do arquivo';
COMMENT ON COLUMN ocr_records.file_size IS 'Tamanho do arquivo em bytes';
COMMENT ON COLUMN ocr_records.extracted_text IS 'Texto extraído pelo OCR';
COMMENT ON COLUMN ocr_records.status IS 'Status: pending, processed, error';
COMMENT ON COLUMN ocr_records.created_at IS 'Data/hora do upload';
