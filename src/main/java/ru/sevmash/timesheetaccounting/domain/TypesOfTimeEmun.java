package ru.sevmash.timesheetaccounting.domain;

import java.util.Random;

public enum TypesOfTimeEmun {
    ADD, REMOVE, BONUS;


    private static final Random RND = new Random();

    public static TypesOfTimeEmun randomType() {
        TypesOfTimeEmun[] types = values();
        return types[RND.nextInt(types.length)];
    }
    }
