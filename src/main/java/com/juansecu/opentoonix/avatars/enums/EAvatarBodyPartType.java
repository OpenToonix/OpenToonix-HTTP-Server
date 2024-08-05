package com.juansecu.opentoonix.avatars.enums;

public enum EAvatarBodyPartType {
    BODY("BODY"),
    COSTUME("COSTUME"),
    EYES("EYE"),
    HEAD("HEAD"),
    MOUTH("MOUTH");

    final String type;

    EAvatarBodyPartType(final String type) {
        this.type = type;
    }
}
