package com.juansecu.opentoonix.verificationtokens.models.entities;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.juansecu.opentoonix.users.models.entities.UserEntity;
import com.juansecu.opentoonix.verificationtokens.enums.EVerificationTokenType;

@Data
@Entity(name = "Verification_tokens")
@NoArgsConstructor
@RequiredArgsConstructor
public class VerificationTokenEntity {
    @Column(name = "Verification_token_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer verificationTokenId;
    @Column(
        name = "Token",
        nullable = false,
        unique = true
    )
    @NonNull
    private UUID token;
    @Column(name = "Type", nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    private EVerificationTokenType type;
    @Column(name = "Uses_count", nullable = false)
    private int usesCount = 0;
    @Column(name = "Metadata")
    private String metadata;
    @Column(name = "User_id", nullable = false)
    private int userId;
    @Column(
        name = "Created_at",
        nullable = false,
        updatable = false
    )
    @CreationTimestamp
    private Date createdAt;
    @Column(name = "Expires_at", nullable = false)
    @NonNull
    private Date expiresAt;
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
    @NonNull
    private UserEntity user;

    public VerificationTokenEntity(
        final UUID token,
        final EVerificationTokenType type,
        final String metadata,
        final Date expiresAt,
        final UserEntity user
    ) {
        this.token = token;
        this.type = type;
        this.metadata = metadata;
        this.expiresAt = expiresAt;
        this.user = user;
    }
}
