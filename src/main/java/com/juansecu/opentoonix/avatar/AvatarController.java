package com.juansecu.opentoonix.avatar;

/* --- Java modules --- */
import java.util.UUID;

/* --- Third-party modules --- */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/avatar")
@RestController
public class AvatarController {
    @Autowired
    private AvatarService avatarService;

    @GetMapping(
        produces = MediaType.IMAGE_PNG_VALUE,
        value = "/{avatarId}"
    )
    public ResponseEntity<byte[]> getAvatar(@PathVariable(name = "avatarId") final UUID avatarId) {
        return this.avatarService.getAvatarImage(avatarId);
    }
}
