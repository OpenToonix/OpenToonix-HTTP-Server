package com.juansecu.opentoonix.avatars;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/avatars")
@RequiredArgsConstructor
@RestController
public class AvatarsController {
    private final AvatarsService avatarsService;

    @GetMapping(
        produces = MediaType.IMAGE_PNG_VALUE,
        value = "/{avatarId}"
    )
    public ResponseEntity<byte[]> getAvatarImage(
        @PathVariable(name = "avatarId") final UUID avatarId
    ) {
        return this.avatarsService.getAvatarImage(avatarId);
    }
}
