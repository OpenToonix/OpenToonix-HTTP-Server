package com.juansecu.opentoonix.user.models.entities;

/* --- Java modules --- */
import java.util.Date;

/* --- Javax modules --- */
import javax.persistence.*;

/* --- Third-party modules --- */
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

/* --- Application modules --- */
import com.juansecu.opentoonix.shared.generators.NumericIdGenerator;
import com.juansecu.opentoonix.user.enums.EUserGender;

/**
 * Represents Users table in the database.
 */
@Data
@Entity(name = "Users")
public class UserEntity {
    @Column(name = "User_id")
    @GeneratedValue(generator = NumericIdGenerator.GENERATOR_NAME)
    @GenericGenerator(
        name = NumericIdGenerator.GENERATOR_NAME,
        strategy = "com.juansecu.opentoonix.shared.generators.NumericIdGenerator"
    )
    @Id
    private Integer userId;
    @Column(name = "Username", nullable = false, unique = true)
    private String username;
    @Column(length = 12, name = "Email", nullable = false, unique = true)
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
}
