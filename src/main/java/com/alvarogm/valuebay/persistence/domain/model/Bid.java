package com.alvarogm.valuebay.persistence.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bids")
public class Bid {

    @Id
    private Integer bidId;
    private Integer fkBill;
    private Integer fkCoin;
    private Integer fkUser;
    private float amount;

    public float getAmount() {
        return amount;
    }

    public Integer getBidId() {
        return bidId;
    }

    public Integer getFkBill() {
        return fkBill;
    }

    public Integer getFkCoin() {
        return fkCoin;
    }

    public Integer getFkUser() {
        return fkUser;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setBidId(Integer bidId) {
        this.bidId = bidId;
    }

    public void setFkBill(Integer fkBill) {
        this.fkBill = fkBill;
    }

    public void setFkCoin(Integer fkCoin) {
        this.fkCoin = fkCoin;
    }

    public void setFkUser(Integer fkUser) {
        this.fkUser = fkUser;
    }
}
