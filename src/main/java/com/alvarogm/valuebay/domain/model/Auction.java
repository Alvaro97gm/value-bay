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

    public Auction(){}
    public Auction(Integer auctionId, Date endTime){
        this.auctionId = auctionId;
        this.endTime = endTime;
    }

    public Integer getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Integer auctionId) {
        this.auctionId = auctionId;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
