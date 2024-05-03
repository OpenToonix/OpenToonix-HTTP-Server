package com.juansecu.opentoonix.avatars.models.entities;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.juansecu.opentoonix.shared.generators.NumericIdGenerator;
import com.juansecu.opentoonix.users.models.entities.UserEntity;

@Data
@Entity(name = "Avatars")
public class AvatarEntity {
    @Column(name = "Avatar_id")
    @GeneratedValue(generator = NumericIdGenerator.GENERATOR_NAME)
    @GenericGenerator(
        name = NumericIdGenerator.GENERATOR_NAME,
        type = NumericIdGenerator.class
    )
    @Id
    private Integer avatarId;
    @Column(name = "Costume", nullable = false)
    private int costume;
    @Column(name = "Skin_color", nullable = false)
    private int skinColor;
    @Column(name = "Head", nullable = false)
    private int head;
    @Column(name = "Head_color", nullable = false)
    private int headColor;
    @Column(name = "Eyes", nullable = false)
    private int eye;
    @Column(name = "Mouth", nullable = false)
    private int mouth;
    @Column(name = "Body", nullable = false)
    private int body;
    @Column(name = "Body_color", nullable = false)
    private int bodyColor;
    @Column(length = 100, name = "Image_path", nullable = false)
    private String imagePath;
    @Column(name = "Credits", nullable = false)
    private int credits = 150;
    @JoinColumn(name = "User_id", nullable = false, referencedColumnName = "User_id", unique = true)
    @OneToOne(
        cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
        targetEntity = UserEntity.class
    )
    private UserEntity userId;
    @Column(name = "Created_at", updatable = false)
    @CreationTimestamp
    private Date createdAt;
    @Column(name = "Updated_at")
    @UpdateTimestamp
    private Date updatedAt;
}
