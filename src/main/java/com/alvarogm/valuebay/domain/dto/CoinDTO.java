package com.alvarogm.valuebay.domain.dto;

import com.alvarogm.valuebay.domain.ConservationStatus;
import lombok.Data;

import java.util.List;

@Data
public class CoinDTO {

    private int lotId;
    private int value;
    private int emissionYear;
    private String conservationStatus;
    private String conservationStatusDetailed;
    private float price;
    private List<String> picturesURLs;

    public CoinDTO(){}
    public CoinDTO(int lotId, int value, int emissionYear, String conservationStatus,
                   String conservationStatusDetailed, float price, List<String> picturesURLs){
        this.lotId = lotId;
        this.value = value;
        this.emissionYear = emissionYear;
        this.conservationStatus = conservationStatus;
        this.conservationStatusDetailed = conservationStatusDetailed;
        this.price = price;
        this.picturesURLs = picturesURLs;
    }

    public int getLotId() {
        return lotId;
    }

    public int getValue() {
        return value;
    }

    public int getEmissionYear() {
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

    public void setLotId(int lotId) {
        this.lotId = lotId;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setEmissionYear(int emissionYear) {
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
