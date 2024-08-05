package com.juansecu.opentoonix.users.models.entities;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
    @Column(name = "Registered_at", updatable = false)
    @CreationTimestamp
    private Date registeredAt;
    @Column(name = "Updated_at")
    @UpdateTimestamp
    private Date updatedAt;
    @OneToOne(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "user"
    )
    private AvatarEntity avatar;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
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
        return true;
    }
}
