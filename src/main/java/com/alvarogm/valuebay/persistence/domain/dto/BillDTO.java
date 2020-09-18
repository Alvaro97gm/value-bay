package com.alvarogm.valuebay.persistence.domain.dto;

import java.util.List;

public class BillDTO {

    private int lotId;
    private int itemValue;
    private int emissionYear;
    private int emissionMonth;
    private int emissionDay;
    private String conservationStatus;
    private String conservationStatusDetailed;
    private float price;
    private Integer fkAuction;
    private List<String> picturesURLs;

    public BillDTO(){}
    public BillDTO(int lotId, int itemValue, int emissionYear, int emissionMonth, int emissionDay,
                   String conservationStatus,  float price){

        this.lotId = lotId;
        this.itemValue = itemValue;
        this.emissionYear = emissionYear;
        this.emissionMonth = emissionMonth;
        this.emissionDay = emissionDay;
        this.conservationStatus = conservationStatus;
        this.price = price;
    }

    public int getLotId() {
        return lotId;
    }

    public Integer getFkAuction() {
        return fkAuction;
    }

    public int getItemValue() {
        return itemValue;
    }

    public int getEmissionYear() {
        return emissionYear;
    }

    public int getEmissionMonth() {
        return emissionMonth;
    }

    public int getEmissionDay() {
        return emissionDay;
    }

    public String getConservationStatus() {
        return conservationStatus;
    }

    public String getConservationStatusDetailed() {
        return conservationStatusDetailed;
    }

    public float getPrice() {
        return price;
    }

    public List<String> getPicturesURLs() {
        return picturesURLs;
    }


    public void setFkAuction(Integer fkAuction) {
        this.fkAuction = fkAuction;
    }

    public void setLotId(int lotId) {
        this.lotId = lotId;
    }

    public void setItemValue(int itemValue) {
        this.itemValue = itemValue;
    }

    public void setEmissionYear(int emissionYear) {
        this.emissionYear = emissionYear;
    }

    public void setEmissionMonth(int emissionMonth) {
        this.emissionMonth = emissionMonth;
    }

    public void setEmissionDay(int emissionDay) {
        this.emissionDay = emissionDay;
    }

    public void setConservationStatus(String conservationStatus) {
        this.conservationStatus = conservationStatus;
    }

    public void setConservationStatusDetailed(String conservationStatusDetailed) {
        this.conservationStatusDetailed = conservationStatusDetailed;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setPicturesURLs(List<String> picturesURLs) {
        this.picturesURLs = picturesURLs;
    }
}
