package com.juansecu.opentoonix.avatar;

/* --- Java modules --- */
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

/* --- Javax modules --- */
import javax.imageio.ImageIO;

/* --- Third-party modules --- */
import org.springframework.beans.factory.annotation.Autowired;
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
        File outputFile = new File("src/main/resources/public/images/toonix/" + uuid + ".png");

        image = ImageIO.read(new ByteArrayInputStream(imageData));
        ImageIO.write(image, "png", outputFile);

        newAvatar.setCostume(newAvatarReqDto.getCostume());
        newAvatar.setSkinColor(newAvatarReqDto.getSkinColor());
        newAvatar.setHead(newAvatarReqDto.getHead());
        newAvatar.setHeadColor(newAvatarReqDto.getHeadColor());
        newAvatar.setEye(newAvatarReqDto.getEye());
        newAvatar.setMouth(newAvatarReqDto.getMouth());
        newAvatar.setBody(newAvatarReqDto.getBody());
        newAvatar.setBodyColor(newAvatarReqDto.getBodyColor());
        newAvatar.setImagePath("/images/toonix/" + uuid + ".png");
        newAvatar.setUserId(user);

        this.avatarDao.save(newAvatar);
    }
}
