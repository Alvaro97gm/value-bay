package com.alvarogm.valuebay.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CommonService {

    /**
     *
     * @return a random integer between 0 - 99999
     */
    public static Integer generate5DigitsId(){
        Random rand = new Random(System.currentTimeMillis());
        return rand.nextInt(99999);
    }
}
