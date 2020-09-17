package com.alvarogm.valuebay.persistence.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "auctions")
public class Auction {

    @Id
    private Integer auctionId;
    private String name;
    private boolean active;
    private Date activationTime;
    private Integer duration;

    public Auction(){}
    public Auction(Integer auctionId){
        this.auctionId = auctionId;
    }

    public Integer getAuctionId() {
        return auctionId;
    }

    public String getName() {
        return name;
    }

    public Date getActivationTime() {
        return activationTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public boolean isActive() {
        return active;
    }

    public void setAuctionId(Integer auctionId) {
        this.auctionId = auctionId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActivationTime(Date activationTime) {
        this.activationTime = activationTime;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
