package com.juansecu.opentoonix.avatars.initializers;

import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AvatarStorageFolderInitializer implements InitializingBean {
    private static final Logger CONSOLE_LOGGER = LogManager.getLogger(AvatarStorageFolderInitializer.class);

    @Value("${avatar.storage-folder-path}")
    private String avatarStorageFolderPath;

    @Override
    public void afterPropertiesSet() throws Exception {
        AvatarStorageFolderInitializer.CONSOLE_LOGGER.info("Creating avatar storage folder...");

        Files.createDirectories(Path.of(this.avatarStorageFolderPath));

        AvatarStorageFolderInitializer.CONSOLE_LOGGER.info(
            "Initialization of avatar storage folder finished successfully"
        );
    }
}
