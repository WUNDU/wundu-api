CREATE TABLE ocr_records (
    id VARCHAR(255) PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    content_type VARCHAR(100) NOT NULL,
    extracted_text TEXT,
    file_size BIGINT NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Índices para acelerar buscas comuns
CREATE INDEX idx_ocr_records_file_name ON ocr_records(file_name);
CREATE INDEX idx_ocr_records_content_type ON ocr_records(content_type);
CREATE INDEX idx_ocr_records_created_at ON ocr_records(created_at);

-- Comentários para documentação
COMMENT ON TABLE ocr_records IS 'Tabela para armazenar resultados do OCR de arquivos processados';
COMMENT ON COLUMN ocr_records.id IS 'Identificador único do registro OCR';
COMMENT ON COLUMN ocr_records.file_name IS 'Nome original do arquivo enviado';
COMMENT ON COLUMN ocr_records.content_type IS 'Tipo de conteúdo (MIME type) do arquivo';
COMMENT ON COLUMN ocr_records.extracted_text IS 'Texto extraído pelo OCR';
COMMENT ON COLUMN ocr_records.file_size IS 'Tamanho do arquivo em bytes';
COMMENT ON COLUMN ocr_records.created_at IS 'Data e hora em que o registro foi criado';
