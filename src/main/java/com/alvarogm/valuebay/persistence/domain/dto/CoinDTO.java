package com.alvarogm.valuebay.persistence.domain.dto;

import java.util.List;

public class CoinDTO {

    private Integer lotId;
    private Integer itemValue;
    private Integer emissionYear;
    private String conservationStatus;
    private String conservationStatusDetailed;
    private float price;
    private List<String> picturesURLs;

    public CoinDTO(){}
    public CoinDTO(Integer lotId, Integer itemValue, Integer emissionYear,
                   String conservationStatus, float price){
        this.lotId = lotId;
        this.itemValue = itemValue;
        this.emissionYear = emissionYear;
        this.conservationStatus = conservationStatus;
        this.price = price;
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

    public String getConservationStatusDetailed() {
        return conservationStatusDetailed;
    }

    public float getPrice() {
        return price;
    }

    public List<String> getPicturesURLs() {
        return picturesURLs;
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
