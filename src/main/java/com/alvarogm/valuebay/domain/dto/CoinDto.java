package com.alvarogm.valuebay.domain.dto;

import com.alvarogm.valuebay.domain.ConservationStatus;
import lombok.Data;

import java.util.ArrayList;

@Data
public class CoinDto {

    private int lotId;
    private int value;
    private int emissionYear;
    private ConservationStatus conservationStatus;
    private float price;
    private ArrayList<String> picturesURLs;
}
