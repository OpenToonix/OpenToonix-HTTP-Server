package com.juansecu.opentoonix.avatar;

/* --- Java modules --- */
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.UUID;

/* --- Javax modules --- */
import javax.imageio.ImageIO;

/* --- Third-party modules --- */
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/* --- Application modules --- */
import com.juansecu.opentoonix.avatar.daos.IAvatarDao;
import com.juansecu.opentoonix.avatar.dtos.requests.NewAvatarReqDto;
import com.juansecu.opentoonix.avatar.models.entities.AvatarEntity;
import com.juansecu.opentoonix.user.models.entities.UserEntity;

/**
 * Contains all data operations related to avatars.
 */
@Service
public class AvatarService {
    private static final String AVATAR_FILE_FORMAT_SUFFIX = ".png";
    private static final Logger CONSOLE_LOGGER = LogManager.getLogger(AvatarService.class);

    @Value("${avatar.storage-folder-path}")
    private String avatarStorageFolderPath;

    @Autowired
    private IAvatarDao avatarDao;

    /**
     * Create a new user's avatar image file
     * and store some of its data in the database.
     *
     * @param newAvatarReqDto New avatar's data
     * @param user User object
     * @throws IOException If the image data cannot be read or saved
     */
    public void createAvatar(NewAvatarReqDto newAvatarReqDto, UserEntity user) throws IOException {
        AvatarEntity newAvatar = new AvatarEntity();
        UUID uuid = UUID.randomUUID();
        BufferedImage image;
        byte[] imageData = Base64.getDecoder().decode(newAvatarReqDto.getImageData());
        File outputFile = Files.createFile(
            Path.of(
                this.avatarStorageFolderPath,
                uuid + AvatarService.AVATAR_FILE_FORMAT_SUFFIX
            )
        )
            .toFile();

        image = ImageIO.read(new ByteArrayInputStream(imageData));
        ImageIO.write(
            image,
            AvatarService.AVATAR_FILE_FORMAT_SUFFIX.replace(".", ""),
            outputFile
        );

        newAvatar.setCostume(newAvatarReqDto.getCostume());
        newAvatar.setSkinColor(newAvatarReqDto.getSkinColor());
        newAvatar.setHead(newAvatarReqDto.getHead());
        newAvatar.setHeadColor(newAvatarReqDto.getHeadColor());
        newAvatar.setEye(newAvatarReqDto.getEye());
        newAvatar.setMouth(newAvatarReqDto.getMouth());
        newAvatar.setBody(newAvatarReqDto.getBody());
        newAvatar.setBodyColor(newAvatarReqDto.getBodyColor());
        newAvatar.setImagePath("/avatar/" + uuid);
        newAvatar.setUserId(user);

        this.avatarDao.save(newAvatar);
    }

    public ResponseEntity<byte[]> getAvatarImage(final UUID avatarId) {
        try(
            final InputStream file = Files.newInputStream(
                Path.of(
                    this.avatarStorageFolderPath,
                    avatarId.toString() + AvatarService.AVATAR_FILE_FORMAT_SUFFIX
                )
            )
        ) {
            final byte[] fileBytes = file.readAllBytes();

            return new ResponseEntity<>(
                fileBytes,
                HttpStatus.OK
            );
        } catch (IOException ioException) {
            AvatarService.CONSOLE_LOGGER.error(
                String.format(
                    "There was an error while retrieving the requested user avatar:%n%s",
                    ioException
                )
            );
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
