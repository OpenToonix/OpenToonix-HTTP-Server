package com.juansecu.opentoonix.user.enums;

/**
 * Contains the assignable genders to a user.
 */
public enum EUserGender {
    FEMALE("FEMALE"),
    MALE("MALE");

    public final String GENDER;

    EUserGender(String gender) {
        this.GENDER = gender;
    }
}
