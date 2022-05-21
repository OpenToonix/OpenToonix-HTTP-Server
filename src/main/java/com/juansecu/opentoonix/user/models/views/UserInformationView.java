package com.juansecu.opentoonix.user.models.views;

/* --- Javax modules --- */
import javax.persistence.*;

/* --- Third-party modules --- */
import lombok.Getter;
import org.hibernate.annotations.Immutable;

/* --- Application modules --- */
import com.juansecu.opentoonix.user.enums.EUserGender;

/**
 * Represents the V_User_information view
 * in database, which will return
 * the necessary information of the user
 * to get them inside the game.
 */
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
    @Column(name = "Credits")
    private int credits;
    @Column(name = "Body")
    private int avatarBody;
    @Column(name = "Body_color")
    private int avatarBodyColor;
    @Column(name = "Costume")
    private int avatarCostume;
    @Column(name = "Eyes")
    private int avatarEyes;
    @Column(name = "Head")
    private int avatarHead;
    @Column(name = "Head_color")
    private int avatarHeadColor;
    @Column(name = "Image_path")
    private String avatarImagePath;
    @Column(name = "Mouth")
    private int avatarMouth;
    @Column(name = "Skin_color")
    private int avatarSkinColor;
}
