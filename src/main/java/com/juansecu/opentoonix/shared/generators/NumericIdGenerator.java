package com.juansecu.opentoonix.shared.generators;

/* --- Java modules --- */
import java.io.Serializable;
import java.util.UUID;

/* --- Third-party modules --- */
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * Generator for unique numerical IDs.
 */
public class NumericIdGenerator implements IdentifierGenerator {
    public static final String GENERATOR_NAME = "NumericIdGenerator";

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object object) throws HibernateException {
        String id = "" + UUID.randomUUID().toString().hashCode();
        return Integer.parseInt(id.replaceAll("-", ""));
    }
}
