package com.juansecu.opentoonix.auth.models.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.juansecu.opentoonix.users.models.entities.UserEntity;

@Data
@Entity(name = "Roles")
public class RoleEntity {
    @Column(name = "Role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer roleId;
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
            name = "Permission_id",
            referencedColumnName = "Permission_id"
        ),
        joinColumns = @JoinColumn(
            name = "Role_id",
            referencedColumnName = "Role_id"
        ),
        name = "Role_permissions"
    )
    @ManyToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    private List<PermissionEntity> permissions;
    @JoinTable(
        inverseJoinColumns = @JoinColumn(
            name = "User_id",
            referencedColumnName = "User_id"
        ),
        joinColumns = @JoinColumn(
            name = "Role_id",
            referencedColumnName = "Role_id"
        ),
        name = "User_roles"
    )
    @ManyToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    private List<UserEntity> users;
}
