package com.juansecu.opentoonix.shared.generators;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class NumericIdGenerator implements IdentifierGenerator {
    public static final String GENERATOR_NAME = "NumericIdGenerator";

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object object) throws HibernateException {
        String id = "" + UUID.randomUUID().toString().hashCode();
        return Integer.parseInt(id.replaceAll("-", ""));
    }
}
