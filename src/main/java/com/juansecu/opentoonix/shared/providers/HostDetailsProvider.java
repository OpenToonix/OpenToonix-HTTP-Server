package com.juansecu.opentoonix.shared.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class HostDetailsProvider {
    @Value("${server.public-host-address}")
    private String publicHostAddress;
    @Value("${SHOULD_USE_SSL:false}")
    private boolean isSslEnabled;
    @Value("${server.should-include-server-port}")
    private boolean shouldIncludeServerPort;
    @Value("${server.port}")
    private int port;

    public String getHostAddress() {
        return this.publicHostAddress.trim();
    }

    public String getHostPath() {
        final String protocol = this.isSslEnabled ? "https" : "http";
        final String serverPort = this.shouldIncludeServerPort
            ? ":" + this.getServerPort()
            : "";

        return protocol + "://" + this.getHostAddress() + serverPort;
    }

    public int getServerPort() {
        return this.port;
    }
}
