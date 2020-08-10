package com.alvarogm.valuebay.domain.dto;

import com.alvarogm.valuebay.domain.ConservationStatus;
import lombok.Data;

import java.util.List;

@Data
public class BillDTO {

    private int lotId;
    private int value;
    private int emissionYear;
    private int emissionMonth;
    private int emissionDay;
    private String conservationStatus;
    private String conservationStatusDetailed;
    private float price;
    private List<String> picturesURLs;

    public BillDTO(){}
    public BillDTO(int lotId, int value, int emissionYear, int emissionMonth, int emissionDay,
                   String conservationStatus, String conservationStatusDetailed, float price,
                   List<String> picturesURLs){

        this.lotId = lotId;
        this.value = value;
        this.emissionYear = emissionYear;
        this.emissionMonth = emissionMonth;
        this.emissionDay = emissionDay;
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


    public void setLotId(int lotId) {
        this.lotId = lotId;
    }

    public void setValue(int value) {
        this.value = value;
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
