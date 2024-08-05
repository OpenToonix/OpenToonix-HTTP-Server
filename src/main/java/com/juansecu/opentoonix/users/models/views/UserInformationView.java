package com.juansecu.opentoonix.users.models.views;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

import com.juansecu.opentoonix.users.enums.EUserGender;

@Entity(name = "V_User_information")
@Getter
@Immutable
public class UserInformationView {
    @Column(name = "User_id")
    @Id
    private Integer userId;
    @Column(name = "Username")
    private String username;
    @Column(name = "Gender")
    @Enumerated(EnumType.STRING)
    private EUserGender gender;
    @Column(name = "Age")
    private int age;
    @Column(name = "Costume_id")
    private int avatarCostume;
    @Column(name = "Skin_color")
    private int avatarSkinColor;
    @Column(name = "Head_id")
    private int avatarHead;
    @Column(name = "Head_color")
    private int avatarHeadColor;
    @Column(name = "Eyes_id")
    private int avatarEyes;
    @Column(name = "Mouth_id")
    private int avatarMouth;
    @Column(name = "Body_id")
    private int avatarBody;
    @Column(name = "Body_color")
    private int avatarBodyColor;
    @Column(name = "Credits")
    private int credits;
    @Column(name = "Image_path")
    private String avatarImagePath;
}
