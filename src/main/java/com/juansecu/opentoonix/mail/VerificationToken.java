package com.juansecu.opentoonix.mail;

/* --- Java modules --- */
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/* --- Javax modules --- */
import javax.persistence.*;

/* --- Third-party modules --- */
import lombok.Data;

/* --- Application modules --- */
import com.juansecu.opentoonix.user.models.entities.UserEntity;

@Entity
@Data
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    @OneToOne (targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn (nullable = false, name = "User_id")
    private UserEntity user;
    private Date expiryDate;

    public Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, EXPIRATION);
        return new Date(cal.getTime().getTime());
    }
}
