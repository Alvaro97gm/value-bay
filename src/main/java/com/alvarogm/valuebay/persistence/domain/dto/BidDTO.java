package com.alvarogm.valuebay.persistence.domain.dto;

import com.alvarogm.valuebay.service.CommonService;

public class BidDTO {

    private Integer bidId;
    private Integer fkUser;
    private Integer fkCoin;
    private Integer fkBill;
    private float amount;

    public BidDTO(){}
    public BidDTO(Integer fkUser,Integer fkCoin, Integer fkBill, float amount){
        this.bidId = CommonService.generate5DigitsId();
        this.fkUser = fkUser;
        this.fkCoin = fkCoin;
        this.fkBill = fkBill;
        this.amount = amount;
    }

    public Integer getFkUser() {
        return fkUser;
    }

    public Integer getFkCoin() {
        return fkCoin;
    }

    public Integer getFkBill() {
        return fkBill;
    }

    public Integer getBidId() {
        return bidId;
    }

    public float getAmount() {
        return amount;
    }

    public void setFkUser(Integer fkUser) {
        this.fkUser = fkUser;
    }

    public void setFkCoin(Integer fkCoin) {
        this.fkCoin = fkCoin;
    }

    public void setFkBill(Integer fkBill) {
        this.fkBill = fkBill;
    }

    public void setBidId(Integer bidId) {
        this.bidId = bidId;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
