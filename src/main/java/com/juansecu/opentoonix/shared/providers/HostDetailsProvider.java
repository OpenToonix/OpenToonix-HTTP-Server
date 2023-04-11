package com.juansecu.opentoonix.shared.providers;

/* --- Java modules --- */
import java.net.InetAddress;
import java.net.UnknownHostException;

/* --- Third-party modules --- */
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class HostDetailsProvider {
    private static final Logger CONSOLE_LOGGER = LogManager.getLogger(HostDetailsProvider.class);

    @Value("${server.port}")
    private int port;

    private HostDetailsProvider() {}

    public String getHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException unknownHostException) {
            HostDetailsProvider.CONSOLE_LOGGER.error(
                String.format(
                    "There was an error while trying to retrieve the host information:%n%s",
                    unknownHostException
                )
            );

            return null;
        }
    }

    public String getHostPath() {
        return "http://" +
            this.getHostAddress() +
            ":" +
            this.getServerPort();
    }

    public int getServerPort() {
        return this.port;
    }
}
