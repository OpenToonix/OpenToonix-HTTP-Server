package com.juansecu.opentoonix.auth.models.entities;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity(name = "Role_permissions")
public class RolePermissionEntity {
    @Column(name = "Role_id", nullable = false)
    @Id
    private Integer roleId;
    @Column(name = "Permission_id", nullable = false)
    @Id
    private Integer permissionId;
    @Column(name = "Assigned_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Date assignedAt;
    @Column(name = "Updated_at", nullable = false)
    @UpdateTimestamp
    private Date updatedAt;
    @JoinColumn(
        insertable = false,
        name = "Role_id",
        referencedColumnName = "Role_id",
        updatable = false
    )
    @ManyToOne(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    private RoleEntity role;
    @JoinColumn(
        insertable = false,
        name = "Permission_id",
        referencedColumnName = "Permission_id",
        updatable = false
    )
    @ManyToOne(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    private PermissionEntity permission;
}
