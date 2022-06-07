package com.police.entities.enums;

public enum CrimeType {
    ANTISOCIAL_BEHAVIOR,
    DOMESTIC_ABUSE,
    ARSON,
    FRAUD,
    RAPE,
    CHILDHOOD_ABUSE,
    HATE_CRIME,
    MURDER,
    VIOLENT_CRIME,
    ROBBERY,
    TERRORISM;

    public int getRisk(){
        return ordinal() + 1;
    }
}
