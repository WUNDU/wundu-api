-- V011 - Imutabilidade em audit_logs e document_logs
-- Criação de triggers e revogação de permissões para garantir que registros não possam ser alterados ou removidos.

CREATE OR REPLACE FUNCTION prevent_document_log_changes()
RETURNS trigger AS $$
BEGIN
  RAISE EXCEPTION 'Document logs são imutáveis. UPDATE/DELETE não são permitidos em document_logs.';
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS document_logs_no_change ON document_logs;
CREATE TRIGGER document_logs_no_change
BEFORE UPDATE OR DELETE ON document_logs
FOR EACH ROW EXECUTE FUNCTION prevent_document_log_changes();

CREATE OR REPLACE FUNCTION prevent_audit_log_changes()
RETURNS trigger AS $$
BEGIN
  RAISE EXCEPTION 'Audit logs são imutáveis. UPDATE/DELETE não são permitidos em audit_logs.';
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS audit_logs_no_change ON audit_logs;
CREATE TRIGGER audit_logs_no_change
BEFORE UPDATE OR DELETE ON audit_logs
FOR EACH ROW EXECUTE FUNCTION prevent_audit_log_changes();

REVOKE UPDATE, DELETE ON document_logs FROM PUBLIC;
REVOKE UPDATE, DELETE ON audit_logs FROM PUBLIC;

COMMENT ON FUNCTION prevent_document_log_changes IS 'Impede UPDATE/DELETE em document_logs para garantir imutabilidade.';
COMMENT ON FUNCTION prevent_audit_log_changes IS 'Impede UPDATE/DELETE em audit_logs para garantir imutabilidade.';
