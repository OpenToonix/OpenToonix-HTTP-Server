package com.juansecu.opentoonix.user.models;

/* --- Third-party modules --- */
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/* --- Application modules --- */
import com.juansecu.opentoonix.user.enums.EUserGender;
import com.juansecu.opentoonix.user.models.views.UserInformationView;

/**
 * Represents the user information
 * that will be used by the client
 * to manage the user's session.
 */
@Getter
public class UserInformationModel {
    @JsonProperty("id")
    private final int userId;
    private final String username;
    private final EUserGender gender;
    private final int age;
    private final int credits;
    private final int avatarBody;
    private final int avatarBodyColor;
    private final int avatarCostume;
    private final int avatarEye;
    private final int avatarHead;
    private final int avatarHeadColor;
    private final String avatarImagePath;
    private final int avatarMouth;
    private final int avatarSkinColor;
    @JsonProperty("cookieUser")
    private final UserCookieModel userCookie;

    private final String registerFeedCode = "la";

    public UserInformationModel(final UserInformationView userInformationView, final UserCookieModel cookieUser) {
        this.userId = userInformationView.getUserId();
        this.username = userInformationView.getUsername();
        this.gender = userInformationView.getGender();
        this.age = userInformationView.getAge();
        this.credits = userInformationView.getCredits();
        this.avatarBody = userInformationView.getAvatarBody();
        this.avatarBodyColor = userInformationView.getAvatarBodyColor();
        this.avatarCostume = userInformationView.getAvatarCostume();
        this.avatarEye = userInformationView.getAvatarEyes();
        this.avatarHead = userInformationView.getAvatarHead();
        this.avatarHeadColor = userInformationView.getAvatarHeadColor();
        this.avatarImagePath = userInformationView.getAvatarImagePath();
        this.avatarMouth = userInformationView.getAvatarMouth();
        this.avatarSkinColor = userInformationView.getAvatarSkinColor();
        this.userCookie = cookieUser;
    }
}
