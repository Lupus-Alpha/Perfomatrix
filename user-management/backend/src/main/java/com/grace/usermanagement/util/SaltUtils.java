package com.grace.usermanagement.util;

import java.util.Random;

public class SaltUtils {
    public static String getSalt(int num){
        char[] chars = "0123456789AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz!@#$%^&*()_+-={}[],./;:\"'\\".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i <num ; i++){
            char aChar  = chars[new Random().nextInt(chars.length)];
            stringBuilder.append(aChar);
        }
        return stringBuilder.toString();
    }

}
