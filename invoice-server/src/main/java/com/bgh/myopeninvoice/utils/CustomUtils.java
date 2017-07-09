/*
 * Copyright 2017 Branislav Cavlin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bgh.myopeninvoice.utils;

import org.joda.time.DateTimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by bcavlin on 14/03/17.
 */
public class CustomUtils {

    private static Logger logger = LoggerFactory.getLogger(CustomUtils.class);

    public static int WEEK_START = DateTimeConstants.MONDAY;
    public static int WEEK_END = DateTimeConstants.SUNDAY;

    public static String encodePassword(String password){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

//    public static Boolean dateFirstOrLast(Date date){
//        return new LocalDate(date).i;
//    }

//    public static void main(String[] args) {
//        System.out.println(CustomUtils.encodePassword("***REMOVED***"));
//    }
}
