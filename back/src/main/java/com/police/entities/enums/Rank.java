package com.police.entities.enums;

import java.util.HashMap;
import java.util.Map;

public enum  Rank {
    OFFICER,
    ADMIN,
    CORPORAL,
    DISPATCHER,
    DETECTIVE,
    SERGEANT,
    LIEUTENANT,
    CAPTAIN;

    private static Map map = new HashMap<>();

    static {
        for (Rank rank: Rank.values()) {
            map.put(rank.ordinal(), rank);
        }
    }

    public static Rank valueOf(int number) {
        return (Rank) map.get(number);
    }

    public int getSkill(){
        return ordinal() + 1;
    }

    }
