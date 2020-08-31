package com.alvarogm.valuebay.persistence.domain.model;

import javax.persistence.*;

@Entity
@Table(name = "coins")
public class Coin {

    @Id
    private Integer lotId;
    private Integer itemValue;
    private Integer emissionYear;
    private String conservationStatus;
    private float price;
    private Integer fkAuction;

    public Coin(){}
    public Coin(Integer lotId, Integer itemValue, Integer emissionYear, String conservationStatus, float price, Integer fkAuction){
        this.lotId = lotId;
        this.itemValue = itemValue;
        this.emissionYear = emissionYear;
        this.conservationStatus = conservationStatus;
        this.price = price;
        this.fkAuction = fkAuction;
    }

    public Integer getLotId() {
        return lotId;
    }

    public Integer getItemValue() {
        return itemValue;
    }

    public Integer getEmissionYear() {
        return emissionYear;
    }

    public String getConservationStatus() {
        return conservationStatus;
    }

    public float getPrice() {
        return price;
    }

    public Integer getFkAuction() {
        return fkAuction;
    }

    public void setLotId(Integer lotId) {
        this.lotId = lotId;
    }

    public void setItemValue(Integer itemValue) {
        this.itemValue = itemValue;
    }

    public void setEmissionYear(Integer emissionYear) {
        this.emissionYear = emissionYear;
    }

    public void setConservationStatus(String conservationStatus) {
        this.conservationStatus = conservationStatus;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setFkAuction(Integer fkAuction) {
        this.fkAuction = fkAuction;
    }
}
