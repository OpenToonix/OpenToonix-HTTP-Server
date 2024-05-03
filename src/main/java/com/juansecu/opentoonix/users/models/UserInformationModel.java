package com.juansecu.opentoonix.users.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import com.juansecu.opentoonix.shared.models.CookieModel;
import com.juansecu.opentoonix.users.enums.EUserGender;
import com.juansecu.opentoonix.users.models.views.UserInformationView;

@Getter
public class UserInformationModel {
    private final int age;
    @JsonProperty("avatarBody")
    private final int avatarBodyId;
    private final int avatarBodyColor;
    @JsonProperty("avatarCostume")
    private final int avatarCostumeId;
    @JsonProperty("avatarEye")
    private final int avatarEyesId;
    @JsonProperty("avatarHead")
    private final int avatarHeadId;
    private final int avatarHeadColor;
    private final String avatarImagePath;
    @JsonProperty("avatarMouth")
    private final int avatarMouthId;
    private final int avatarSkinColor;
    private final int credits;
    private final EUserGender gender;
    @JsonIgnore
    private final String hostPath;
    private final String registerFeedCode;
    @JsonProperty("cookieUser")
    private final CookieModel userCookie;
    @JsonProperty("id")
    private final int userId;
    private final String username;


    public UserInformationModel(
        final UserInformationView userInformation,
        final CookieModel userAuthCookie,
        final String hostPath
    ) {
        this.age = userInformation.getAge();
        this.avatarBodyId = userInformation.getAvatarBody();
        this.avatarBodyColor = userInformation.getAvatarBodyColor();
        this.avatarCostumeId = userInformation.getAvatarCostume();
        this.avatarEyesId = userInformation.getAvatarEyes();
        this.avatarHeadId = userInformation.getAvatarHead();
        this.avatarHeadColor = userInformation.getAvatarHeadColor();
        this.avatarImagePath = userInformation.getAvatarImagePath();
        this.avatarMouthId = userInformation.getAvatarMouth();
        this.avatarSkinColor = userInformation.getAvatarSkinColor();
        this.credits = userInformation.getCredits();
        this.gender = userInformation.getGender();
        this.hostPath = hostPath;
        this.registerFeedCode = "la";
        this.userCookie = userAuthCookie;
        this.userId = userInformation.getUserId();
        this.username = userInformation.getUsername();
    }

    public String getAvatarImagePath() {
        return this.hostPath + this.avatarImagePath;
    }
}
