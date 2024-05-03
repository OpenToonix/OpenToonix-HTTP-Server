package com.juansecu.opentoonix.cubes.enums;

public enum ECubeItemType {
    COVERING("covering"),
    MOBILE("mobile"),
    STRUCTURE("structure");

    final String cubeItemType;

    ECubeItemType(final String cubeItemType) {
        this.cubeItemType = cubeItemType;
    }
}
