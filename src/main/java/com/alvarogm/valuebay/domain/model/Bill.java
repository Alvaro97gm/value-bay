package com.alvarogm.valuebay.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bills")
public class Bill {

    @Id
    private int lotId;
    private int itemValue;
    private int emissionYear;
    private int emissionMonth;
    private int emissionDay;
    private String conservationStatus;
    private float price;
}