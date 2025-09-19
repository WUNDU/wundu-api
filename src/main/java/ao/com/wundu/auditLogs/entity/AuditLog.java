package ao.com.wundu.auditLogs.entity;

import ao.com.wundu.auditLogs.enums.ActionState;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "actor_id", nullable = false)
    private String actorId;

    @Column(name = "target_user_id")
    private String targetUserId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private ActionState action;

    @Column(name = "changed_fields", columnDefinition = "TEXT")
    private String changedFields;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public AuditLog() {}

    public AuditLog(String actorId, String targetUserId, ActionState action, String changedFields) {
        this.actorId = actorId;
        this.targetUserId = targetUserId;
        this.action = action;
        this.changedFields = changedFields;
    }

    public String getId() {
        return id;
    }

    public String getActorId() {
        return actorId;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public ActionState getAction() {
        return action;
    }

    public String getChangedFields() {
        return changedFields;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public void setAction(ActionState action) {
        this.action = action;
    }

    public void setChangedFields(String changedFields) {
        this.changedFields = changedFields;
    }
}
