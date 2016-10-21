package com.fourtyseven.notificationpoc;

/**
 * Created by dell on 10/6/2016.
 */
public class Apputils {

    public static  boolean isThetypeOfThem(String string, String... arrstring) {
        for (int i = 0; i < arrstring.length; i++) {
            if (string == null || !string.equalsIgnoreCase(arrstring[i])) continue;
            return true;
        }
        return false;
    }
}
