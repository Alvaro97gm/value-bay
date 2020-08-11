package com.alvarogm.valuebay.domain.dto;

import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.List;

public class AuctionDTO {

    private Integer auctionId;
    private List<Integer> lotIds;
    private Date endTime;
    private boolean active;

    public AuctionDTO(){}
    public AuctionDTO(Integer auctionId, Date endTime, boolean active){
        this.auctionId = auctionId;
        this.endTime = endTime;
        this.active = active;
    }


    public Integer getAuctionId() {
        return auctionId;
    }

    public Date getEndTime() {
        return endTime;
    }

    public List<Integer> getLotIds() {
        return lotIds;
    }

    public boolean isActive(){
        return active;
    }


    public void setAuctionId(Integer auctionId) {
        this.auctionId = auctionId;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setLotIds(List<Integer> lotIds) {
        this.lotIds = lotIds;
    }

    public void setActive(boolean active){
        this.active = active;
    }
}
