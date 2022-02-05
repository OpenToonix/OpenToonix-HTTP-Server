package com.juansecu.opentoonix.avatar.dtos.requests;

/* --- Third-party modules --- */
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Represents new avatar's data.
 */
@Data
public class NewAvatarReqDto {
    private int costume;
    private int skinColor;
    private int head;
    private int headColor;
    private int eye;
    private int mouth;
    private int body;
    private int bodyColor;
    private String imageData;
    private String imagePath;

    /**
     * Get NewAvatarReqDto object from parsed JSON.
     *
     * @param parsedAvatarReqDto Parsed data from NewAvatarReqDto object
     * @return NewAvatarReqDto
     */
    public NewAvatarReqDto getNewAvatarReqDtoFromString(String parsedAvatarReqDto) {
        try {
            return new Gson().fromJson(parsedAvatarReqDto, NewAvatarReqDto.class);
        } catch (JsonSyntaxException jsonSyntaxException) {
            jsonSyntaxException.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, jsonSyntaxException.getMessage());
        }
    }
}
