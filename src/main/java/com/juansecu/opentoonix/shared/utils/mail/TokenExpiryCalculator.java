package com.juansecu.opentoonix.shared.utils.mail;

/* --- Java modules --- */
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/* --- Third-party modules --- */
import org.springframework.stereotype.Component;

@Component
public class TokenExpiryCalculator {
    private static final int EXPIRATION = 60 * 24;

    public Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, EXPIRATION);
        return new Date(cal.getTime().getTime());
    }
}
