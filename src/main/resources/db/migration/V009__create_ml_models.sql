-- V007 - Criação da tabela de modelos de machine learning

CREATE TABLE ml_models (
    id VARCHAR(255) PRIMARY KEY,
    model_name VARCHAR(100) NOT NULL,
    version VARCHAR(50) NOT NULL,
    accuracy NUMERIC(5,2),
    kind VARCHAR(20) NOT NULL CHECK (kind IN ('ocr','nlp')),
    trained_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT false
);

-- Índices
CREATE INDEX idx_ml_models_kind ON ml_models(kind);
CREATE INDEX idx_ml_models_active ON ml_models(is_active);

-- Comentários
COMMENT ON TABLE ml_models IS 'Modelos de Machine Learning usados pela Wundu (OCR e NLP)';
COMMENT ON COLUMN ml_models.id IS 'Identificador único do modelo';
COMMENT ON COLUMN ml_models.model_name IS 'Nome do modelo (ex: bert-base-portuguese)';
COMMENT ON COLUMN ml_models.version IS 'Versão do modelo treinado';
COMMENT ON COLUMN ml_models.accuracy IS 'Taxa de acurácia do modelo';
COMMENT ON COLUMN ml_models.kind IS 'Tipo do modelo: ocr ou nlp';
COMMENT ON COLUMN ml_models.trained_at IS 'Data/hora de treinamento do modelo';
COMMENT ON COLUMN ml_models.is_active IS 'Indica se o modelo está ativo em produção';
