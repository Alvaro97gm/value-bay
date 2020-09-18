package com.alvarogm.valuebay.persistence.domain.dto;

public class BidDTO {

    private Integer bidId;
    private Integer fk_user;
    private Integer fk_lot_c;
    private Integer fk_lot_m;
    private float amount;

    public Integer getFk_user() {
        return fk_user;
    }

    public Integer getFk_lot_c() {
        return fk_lot_c;
    }

    public Integer getFk_lot_m() {
        return fk_lot_m;
    }

    public Integer getBidId() {
        return bidId;
    }

    public float getAmount() {
        return amount;
    }

    public void setFk_user(Integer fk_user) {
        this.fk_user = fk_user;
    }

    public void setFk_lot_c(Integer fk_lot_c) {
        this.fk_lot_c = fk_lot_c;
    }

    public void setFk_lot_m(Integer fk_lot_m) {
        this.fk_lot_m = fk_lot_m;
    }

    public void setBidId(Integer bidId) {
        this.bidId = bidId;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
