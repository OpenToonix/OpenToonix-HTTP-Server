package com.juansecu.opentoonix.shared.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class HostDetailsProvider {
    @Value("${server.public-address}")
    private String publicAddress;

    public String getPublicAddress() {
        return this.publicAddress.trim();
    }
}
