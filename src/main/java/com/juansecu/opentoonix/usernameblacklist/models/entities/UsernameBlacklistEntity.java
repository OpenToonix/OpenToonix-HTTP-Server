package com.juansecu.opentoonix.usernameblacklist.models.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.juansecu.opentoonix.shared.generators.NumericIdGenerator;

@Data
@Entity(name = "Username_blacklist")
public class UsernameBlacklistEntity {
    @Column(name = "Username_id")
    @GeneratedValue(generator = NumericIdGenerator.GENERATOR_NAME)
    @GenericGenerator(
            name = NumericIdGenerator.GENERATOR_NAME,
            type = NumericIdGenerator.class
    )
    @Id
    private Integer usernameId;
    @Column(length = 12, name = "Username", nullable = false, unique = true)
    private String username;
    @Column(name = "Added_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Date addedAt;
    @Column(name = "Updated_at", nullable = false)
    @UpdateTimestamp
    private Date updatedAt;
}
