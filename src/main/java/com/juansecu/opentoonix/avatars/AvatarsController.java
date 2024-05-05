package com.juansecu.opentoonix.avatars;

import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juansecu.opentoonix.shared.dtos.responses.BaseResDto;

@RequestMapping("/avatars")
@RequiredArgsConstructor
@RestController
@Tag(
    description = "Avatar operations",
    name = "Avatars"
)
public class AvatarsController {
    private final AvatarsService avatarsService;

    @ApiResponse(
        content = {
            @Content(
                mediaType = MediaType.IMAGE_PNG_VALUE,
                schema = @Schema(
                    type = "string",
                    format = "binary"
                )
            )
        },
        description = "Returns the avatar image.",
        responseCode = "200"
    )
    @ApiResponse(
        content = {
            @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(
                    implementation = BaseResDto.class
                )
            )
        },
        description = "The authentication token is missing or invalid.",
        responseCode = "401"
    )
    @ApiResponse(
        content = {
            @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(
                    implementation = BaseResDto.class
                )
            )
        },
        description = "There was an error while trying to get the avatar image.",
        responseCode = "500"
    )
    @Operation(
        description = "Get the requested avatar image.",
        security = {
            @SecurityRequirement(
                name = "AuthToken"
            )
        },
        summary = "Get the requested avatar image"
    )
    @GetMapping(
        produces = MediaType.IMAGE_PNG_VALUE,
        value = "/{avatarId}"
    )
    public ResponseEntity<Object> getAvatarImage(
        @PathVariable(name = "avatarId") final UUID avatarId
    ) {
        return this.avatarsService.getAvatarImage(avatarId);
    }
}
