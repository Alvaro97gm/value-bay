package com.alvarogm.valuebay.persistence.domain.dto;

import java.util.Date;
import java.util.List;

public class AuctionDTO {

    private Integer auctionId;
    private String name;
    private boolean active;
    private List<Integer> lotIds;
    private Date activationTime;
    private Integer duration;

    public AuctionDTO(){}
    public AuctionDTO(
        Integer auctionId, String name, boolean active, Date activationTime, Integer duration
    ){
        this.auctionId = auctionId;
        this.name = name;
        this.active = active;
        this.activationTime = activationTime;
        this.duration = duration;
    }

    public Integer getAuctionId() {
        return auctionId;
    }

    public String getName() {
        return name;
    }

    public Integer getDuration() {
        return duration;
    }

    public Date getActivationTime() {
        return activationTime;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setActivationTime(Date activationTime) {
        this.activationTime = activationTime;
    }

    public void setLotIds(List<Integer> lotIds) {
        this.lotIds = lotIds;
    }

    public void setActive(boolean active){
        this.active = active;
    }
}
