package com.juansecu.opentoonix.auth.models.entities;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.juansecu.opentoonix.users.models.entities.UserEntity;

@Entity(name = "User_permissions")
@Getter
@Setter
public class UserPermissionEntity {
    @Column(name = "User_id", nullable = false)
    @Id
    private Integer userId;
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
        name = "User_id",
        referencedColumnName = "User_id",
        updatable = false
    )
    @ManyToOne(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    private UserEntity user;
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
