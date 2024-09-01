package com.juansecu.opentoonix.users.models.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.juansecu.opentoonix.auth.models.entities.PermissionEntity;
import com.juansecu.opentoonix.auth.models.entities.RoleEntity;
import com.juansecu.opentoonix.avatars.models.entities.AvatarEntity;
import com.juansecu.opentoonix.shared.generators.NumericIdGenerator;
import com.juansecu.opentoonix.users.enums.EUserGender;

@Data
@Entity(name = "Users")
public class UserEntity implements UserDetails {
    @Column(name = "User_id")
    @GeneratedValue(generator = NumericIdGenerator.GENERATOR_NAME)
    @GenericGenerator(
        name = NumericIdGenerator.GENERATOR_NAME,
        type = NumericIdGenerator.class
    )
    @Id
    private Integer userId;
    @Column(length = 12, name = "Username", nullable = false, unique = true)
    private String username;
    @Column(length = 50, name = "Email", nullable = false, unique = true)
    private String email;
    @Column(length = 2, name = "Age", nullable = false)
    private int age;
    @Column(name = "Birthdate", nullable = false)
    private Date birthdate;
    @Column(name = "Gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private EUserGender gender;
    @Column(length = 60, name = "Password", nullable = false)
    private String password;
    @Column(name = "Registered_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Date registeredAt;
    @Column(name = "Updated_at", nullable = false)
    @UpdateTimestamp
    private Date updatedAt;
    @OneToOne(
        cascade = CascadeType.ALL,
        mappedBy = "user"
    )
    private AvatarEntity avatar;
    @JoinTable(
        inverseJoinColumns = @JoinColumn(
            name = "Permission_id",
            referencedColumnName = "Permission_id"
        ),
        joinColumns = @JoinColumn(
            name = "User_id",
            referencedColumnName = "User_id"
        ),
        name = "User_permissions"
    )
    @ManyToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    private List<PermissionEntity> permissions;
    @JoinTable(
        inverseJoinColumns = @JoinColumn(
            name = "Role_id",
            referencedColumnName = "Role_id"
        ),
        joinColumns = @JoinColumn(
            name = "User_id",
            referencedColumnName = "User_id"
        ),
        name = "User_roles"
    )
    @ManyToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    private List<RoleEntity> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<GrantedAuthority> authorities = new ArrayList<>();

        this.roles.forEach(role ->
            authorities.add(new SimpleGrantedAuthority(role.getName()))
        );

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.getAuthorities().contains(
            new SimpleGrantedAuthority("USER")
        );
    }
}
