package com.juansecu.opentoonix.auth.models.entities;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.juansecu.opentoonix.users.models.entities.UserEntity;

@Data
@Entity(name = "User_roles")
public class UserRoleEntity {
    @Column(name = "User_id", nullable = false)
    @Id
    private Integer userId;
    @Column(name = "Role_id", nullable = false)
    @Id
    private Integer roleId;
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
        name = "Role_id",
        referencedColumnName = "Role_id",
        updatable = false
    )
    @ManyToOne(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    private RoleEntity role;
}
