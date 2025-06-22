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
    @Column(name = "Costume_id", nullable = true)
    private Integer costumeId;
    @Column(name = "Skin_color", nullable = false)
    private int skinColor;
    @Column(name = "Head_id", nullable = false)
    private int headId;
    @Column(name = "Head_color", nullable = false)
    private int headColor;
    @Column(name = "Eyes_id", nullable = false)
    private int eyesId;
    @Column(name = "Mouth_id", nullable = false)
    private int mouth;
    @Column(name = "Body_id", nullable = false)
    private int bodyId;
    @Column(name = "Body_color", nullable = false)
    private int bodyColor;
    @Column(length = 100, name = "Image_path", nullable = false)
    private String imagePath;
    @Column(name = "Credits", nullable = false)
    private int credits = 150;
    @Column(name = "User_id", nullable = false, unique = true)
    private int userId;
    @Column(name = "Created_at", updatable = false)
    @CreationTimestamp
    private Date createdAt;
    @Column(name = "Updated_at")
    @UpdateTimestamp
    private Date updatedAt;
    @JoinColumn(
        insertable = false,
        name = "User_id",
        referencedColumnName = "User_id",
        updatable = false
    )
    @OneToOne(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    private UserEntity user;

    @PrePersist
    @PreUpdate
    private void prePersistAndUpdate() {
        if (this.costumeId == 0) this.costumeId = null;
    }
}
