package com.dzhao.example.utility;

public class SaltGenerator {

    public static void main(String[] args){
        String salt = PasswordHelper.getSalt();
        System.out.println("Salt number is: " + salt);
    }

}
