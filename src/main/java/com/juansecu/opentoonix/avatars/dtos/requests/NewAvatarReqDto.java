package com.juansecu.opentoonix.avatars.dtos.requests;

import com.google.gson.Gson;
import lombok.Data;

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

    public NewAvatarReqDto getNewAvatarReqDtoFromString(String parsedAvatarReqDto) {
        return new Gson().fromJson(parsedAvatarReqDto, NewAvatarReqDto.class);
    }
}
