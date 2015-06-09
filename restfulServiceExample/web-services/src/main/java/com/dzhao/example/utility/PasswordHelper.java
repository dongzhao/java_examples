package com.dzhao.example.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordHelper {
    public static String ALGORITHM_MD5 = "MD5";
    public static String ALGORITHM_SHA1 = "SHA-1";
    public static String ALGORITHM_SHA256 = "SHA-256";
    public static String ALGORITHM_SHA384 = "SHA-384";
    public static String ALGORITHM_SHA512 = "SHA-512";

    private static String ALGORITHM_RANDOM = "SHA1PRNG";
    private static int DEFAULT_SALT_SIZE = 16;

    private PasswordHelper(){}

    public static String getSecurePassword(String plainTextPassword){
        return getSecurePassword(ALGORITHM_MD5, plainTextPassword, null);
    }

    public static String getSecurePassword(String plainTextPassword, String salt){
        return getSecurePassword(ALGORITHM_MD5, plainTextPassword, null);
    }

    public static String getSecurePassword(String algorithm, String plainTextPassword, String salt){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance( (algorithm==null || algorithm.isEmpty()) ? ALGORITHM_MD5 : algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return plainTextPassword;
        }
        if(salt!=null && !salt.isEmpty()){
            md.update(salt.getBytes());
        }
        byte[] bytes = md.digest(plainTextPassword.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static String getSalt(){
        SecureRandom sr = null;
        try {
            sr = SecureRandom.getInstance(ALGORITHM_RANDOM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        byte[] salt = new byte[DEFAULT_SALT_SIZE];
        sr.nextBytes(salt);
        return salt.toString();
    }

}
