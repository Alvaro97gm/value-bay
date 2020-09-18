package com.alvarogm.valuebay.persistence.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bids")
public class Bid {

    @Id
    private Integer bidId;
    private Integer fk_lot_c;
    private Integer fk_lot_b;
    private Integer fk_user;
    private float amount;

    public float getAmount() {
        return amount;
    }

    public Integer getBidId() {
        return bidId;
    }

    public Integer getFk_lot_b() {
        return fk_lot_b;
    }

    public Integer getFk_lot_c() {
        return fk_lot_c;
    }

    public Integer getFk_user() {
        return fk_user;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setBidId(Integer bidId) {
        this.bidId = bidId;
    }

    public void setFk_lot_b(Integer fk_lot_b) {
        this.fk_lot_b = fk_lot_b;
    }

    public void setFk_lot_c(Integer fk_lot_c) {
        this.fk_lot_c = fk_lot_c;
    }

    public void setFk_user(Integer fk_user) {
        this.fk_user = fk_user;
    }
}
