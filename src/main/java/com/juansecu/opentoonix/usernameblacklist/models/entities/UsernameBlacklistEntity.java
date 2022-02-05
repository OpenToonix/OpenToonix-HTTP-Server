package com.juansecu.opentoonix.usernameblacklist.models.entities;

/* --- Java modules --- */
import java.util.Date;

/* --- javax modules --- */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/* --- Third-party modules --- */
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

/* --- Application modules --- */
import com.juansecu.opentoonix.shared.generators.NumericIdGenerator;

/**
 * Represents the Username_blacklist table in the database.
 */
@Data
@Entity(name = "Username_blacklist")
public class UsernameBlacklistEntity {
    @Column(name = "Username_id")
    @GeneratedValue(generator = NumericIdGenerator.GENERATOR_NAME)
    @GenericGenerator(
            name = NumericIdGenerator.GENERATOR_NAME,
            strategy = "com.juansecu.opentoonix.shared.generators.NumericIdGenerator"
    )
    @Id
    private Integer usernameId;
    @Column(length = 12, name = "Username", nullable = false, unique = true)
    private String username;
    @Column(name = "Added_at", updatable = false)
    @CreationTimestamp
    private Date addedAt;
    @Column(name = "Updated_at")
    @UpdateTimestamp
    private Date updatedAt;
}
