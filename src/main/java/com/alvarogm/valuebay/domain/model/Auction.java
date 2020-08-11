package com.alvarogm.valuebay.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "auctions")
public class Auction {

    @Id
    private Integer auctionId;
    private Date endTime;
    private boolean active;

    public Auction(){}
    public Auction(Integer auctionId, Date endTime){
        this.auctionId = auctionId;
        this.endTime = endTime;
    }


    public Integer getAuctionId() {
        return auctionId;
    }

    public Date getEndTime() {
        return endTime;
    }

    public boolean isActive() {
        return active;
    }


    public void setAuctionId(Integer auctionId) {
        this.auctionId = auctionId;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
