package com.juansecu.opentoonix.auth.models.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.juansecu.opentoonix.users.models.entities.UserEntity;

@Data
@Entity(name = "Permissions")
public class PermissionEntity {
    @Column(name = "Permission_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer permissionId;
    @Column(length = 30, name = "Name", nullable = false, unique = true)
    private String name;
    @Column(length = 100, name = "Description", nullable = false, unique = true)
    private String description;
    @Column(name = "Added_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Date addedAt;
    @Column(name = "Updated_at", nullable = false)
    @UpdateTimestamp
    private Date updatedAt;
    @JoinTable(
        inverseJoinColumns = @JoinColumn(
            name = "Role_id",
            referencedColumnName = "Role_id"
        ),
        joinColumns = @JoinColumn(
            name = "Permission_id",
            referencedColumnName = "Permission_id"
        ),
        name = "Role_permissions"
    )
    @ManyToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    private List<RoleEntity> roles;
    @JoinTable(
        inverseJoinColumns = @JoinColumn(
            name = "User_id",
            referencedColumnName = "User_id"
        ),
        joinColumns = @JoinColumn(
            name = "Permission_id",
            referencedColumnName = "Permission_id"
        ),
        name = "User_permissions"
    )
    @ManyToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    private List<UserEntity> users;
}
