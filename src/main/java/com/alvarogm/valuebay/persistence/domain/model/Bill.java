package com.alvarogm.valuebay.persistence.domain.model;

import javax.persistence.*;

@Entity
@Table(name = "bills")
public class Bill {

    @Id
    private Integer lotId;
    private Integer itemValue;
    private Integer emissionYear;
    private Integer emissionMonth;
    private Integer emissionDay;
    private String conservationStatus;
    private float price;
    private Integer fkAuction;

    public Bill(){}
    public Bill(Integer lotId, Integer itemValue, Integer emissionYear, Integer emissionMonth,
                Integer emissionDay, String conservationStatus, float price, Integer fkAuction){

        this.lotId = lotId;
        this.itemValue = itemValue;
        this.emissionYear = emissionYear;
        this.emissionMonth = emissionMonth;
        this.emissionDay = emissionDay;
        this.conservationStatus = conservationStatus;
        this.price = price;
        this.fkAuction = fkAuction;
    }

    public float getPrice() {
        return price;
    }

    public Integer getEmissionDay() {
        return emissionDay;
    }

    public Integer getEmissionMonth() {
        return emissionMonth;
    }

    public Integer getEmissionYear() {
        return emissionYear;
    }

    public Integer getItemValue() {
        return itemValue;
    }

    public Integer getLotId() {
        return lotId;
    }

    public String getConservationStatus() {
        return conservationStatus;
    }

    public Integer getFkAuction() {
        return fkAuction;
    }

    public void setConservationStatus(String conservationStatus) {
        this.conservationStatus = conservationStatus;
    }

    public void setEmissionDay(Integer emissionDay) {
        this.emissionDay = emissionDay;
    }

    public void setEmissionMonth(Integer emissionMonth) {
        this.emissionMonth = emissionMonth;
    }

    public void setEmissionYear(Integer emissionYear) {
        this.emissionYear = emissionYear;
    }

    public void setItemValue(Integer itemValue) {
        this.itemValue = itemValue;
    }

    public void setLotId(Integer lotId) {
        this.lotId = lotId;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setFkAuction(Integer fkAuction) {
        this.fkAuction = fkAuction;
    }
}