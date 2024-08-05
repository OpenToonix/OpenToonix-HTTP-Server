package com.juansecu.opentoonix.avatars;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.UUID;
import javax.imageio.ImageIO;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.juansecu.opentoonix.avatars.enums.EGetAvatarImageError;
import com.juansecu.opentoonix.avatars.dtos.requests.NewAvatarReqDto;
import com.juansecu.opentoonix.avatars.models.entities.AvatarEntity;
import com.juansecu.opentoonix.avatars.repositories.IAvatarsRepository;
import com.juansecu.opentoonix.shared.dtos.responses.BaseResDto;
import com.juansecu.opentoonix.users.models.entities.UserEntity;

@RequiredArgsConstructor
@Service
public class AvatarsService {
    private static final String AVATAR_FILE_FORMAT_SUFFIX = ".png";
    private static final Logger CONSOLE_LOGGER = LogManager.getLogger(AvatarsService.class);

    @Value("${avatar.storage-folder-path}")
    private String avatarStorageFolderPath;
    @Value("${server.servlet.context-path}")
    private String contextPath;

    private final IAvatarsRepository avatarDao;

    public void createAvatar(
        final NewAvatarReqDto newAvatarReqDto,
        final UserEntity user
    ) throws IOException {
        BufferedImage image;

        final AvatarEntity newAvatar = new AvatarEntity();
        final UUID uuid = UUID.randomUUID();
        final byte[] imageData = Base64.getDecoder().decode(newAvatarReqDto.getImageData());
        final File outputFile = Files
            .createFile(
                Path.of(
                    this.avatarStorageFolderPath,
                    uuid + AvatarsService.AVATAR_FILE_FORMAT_SUFFIX
                )
            )
            .toFile();

        AvatarsService.CONSOLE_LOGGER.info(
            "Creating avatar with the UUID {} for user {}...",
            uuid,
            user.getUsername()
        );

        image = ImageIO.read(new ByteArrayInputStream(imageData));
        ImageIO.write(
            image,
            AvatarsService.AVATAR_FILE_FORMAT_SUFFIX.replace(".", ""),
            outputFile
        );

        newAvatar.setCostumeId(newAvatarReqDto.getCostume());
        newAvatar.setSkinColor(newAvatarReqDto.getSkinColor());
        newAvatar.setHeadId(newAvatarReqDto.getHead());
        newAvatar.setHeadColor(newAvatarReqDto.getHeadColor());
        newAvatar.setEyesId(newAvatarReqDto.getEye());
        newAvatar.setMouth(newAvatarReqDto.getMouth());
        newAvatar.setBodyId(newAvatarReqDto.getBody());
        newAvatar.setBodyColor(newAvatarReqDto.getBodyColor());
        newAvatar.setImagePath(this.contextPath + "/avatars/" + uuid);
        newAvatar.setUserId(user.getUserId());

        this.avatarDao.save(newAvatar);

        AvatarsService.CONSOLE_LOGGER.info(
            "Avatar with the UUID {} for user {} has been created successfully",
            uuid,
            user.getUsername()
        );
    }

    public ResponseEntity<Object> getAvatarImage(final UUID avatarId) {
        try(
            final InputStream file = Files.newInputStream(
                Path.of(
                    this.avatarStorageFolderPath,
                    avatarId.toString() + AvatarsService.AVATAR_FILE_FORMAT_SUFFIX
                )
            )
        ) {
            final byte[] fileBytes = file.readAllBytes();

            return new ResponseEntity<>(
                fileBytes,
                HttpStatus.OK
            );
        } catch (IOException ioException) {
            AvatarsService.CONSOLE_LOGGER.error(
                "There was an error while retrieving the requested user avatar: {}",
                ioException.getMessage()
            );

            return new ResponseEntity<>(
                new BaseResDto<>(
                    EGetAvatarImageError.ERROR_RETRIEVING_AVATAR_IMAGE,
                    false
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
