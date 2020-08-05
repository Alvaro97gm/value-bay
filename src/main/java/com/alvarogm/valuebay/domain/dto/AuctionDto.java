package com.alvarogm.valuebay.domain.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class AuctionDto {

    private int auctionId;
    private ArrayList<Integer> lotIds;
    private Date endTime;
}
