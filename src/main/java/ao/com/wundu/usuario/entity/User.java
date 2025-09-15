package ao.com.wundu.usuario.entity;

import ao.com.wundu.usuario.enums.PlanType;
import ao.com.wundu.usuario.enums.Role;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 200)
    private String password;

    @Column(name = "phone_number", nullable = false, unique = true, length = 20)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 25)
    private Role role = Role.CLIENTE;

    @Enumerated(EnumType.STRING)
    @Column(name = "plan_type", length = 10)
    private PlanType planType = PlanType.FREE;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "created_by")
    private String createBy;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "plan_start")
    private LocalDateTime planStart;

    @Column(name = "plan_end")
    private LocalDateTime planEnd;

    @Column(name = "is_trial")
    private Boolean isTrial = false;

    public User() {
    }

    public User(String id, String name, String email, String password, String phoneNumber, Role role, PlanType planType, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime lastLogin, String createBy, String modifiedBy, Boolean isActive, LocalDateTime planStart, LocalDateTime planEnd, Boolean isTrial) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.planType = planType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lastLogin = lastLogin;
        this.createBy = createBy;
        this.modifiedBy = modifiedBy;
        this.isActive = isActive;
        this.planStart = planStart;
        this.planEnd = planEnd;
        this.isTrial = isTrial;
    }

    public User(String name, String email, String password, String phoneNumber, Role role, PlanType planType, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime lastLogin, String createBy, String modifiedBy, Boolean isActive, LocalDateTime planStart, LocalDateTime planEnd, Boolean isTrial) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.planType = planType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lastLogin = lastLogin;
        this.createBy = createBy;
        this.modifiedBy = modifiedBy;
        this.isActive = isActive;
        this.planStart = planStart;
        this.planEnd = planEnd;
        this.isTrial = isTrial;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public PlanType getPlanType() {
        return planType;
    }

    public void setPlanType(PlanType planType) {
        this.planType = planType;
    }

    public LocalDateTime getPlanStart() {
        return planStart;
    }

    public void setPlanStart(LocalDateTime planStart) {
        this.planStart = planStart;
    }

    public LocalDateTime getPlanEnd() {
        return planEnd;
    }

    public void setPlanEnd(LocalDateTime planEnd) {
        this.planEnd = planEnd;
    }

    public Boolean getTrial() {
        return isTrial;
    }

    public void setTrial(Boolean trial) {
        isTrial = trial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}