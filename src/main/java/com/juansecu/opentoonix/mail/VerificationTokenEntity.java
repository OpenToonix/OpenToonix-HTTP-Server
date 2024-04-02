package com.juansecu.opentoonix.mail;

/* --- Java modules --- */
import java.util.Date;

/* --- Javax modules --- */
import javax.persistence.*;

/* --- Third-party modules --- */
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;


/* --- Application modules --- */
import com.juansecu.opentoonix.user.models.entities.UserEntity;
import com.juansecu.opentoonix.shared.generators.NumericIdGenerator;

@Entity(name = "Verification_tokens")
@Data
public class VerificationTokenEntity {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(generator = NumericIdGenerator.GENERATOR_NAME)
    @GenericGenerator(
            name = NumericIdGenerator.GENERATOR_NAME,
            strategy = "com.juansecu.opentoonix.shared.generators.NumericIdGenerator"
    )
    private Long id;
    private String token;
    @OneToOne (targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn (nullable = false, name = "User_id")
    private UserEntity user;
    private Date expiryDate;

}
