package com.bgh.myopeninvoice.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by bcavlin on 14/03/17.
 */
public class CustomUtils {

    private static Logger logger = LoggerFactory.getLogger(CustomUtils.class);

    public static String encodePassword(String password){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

//    public static void main(String[] args) {
//        System.out.println(CustomUtils.encodePassword("letmein"));
//    }
}
