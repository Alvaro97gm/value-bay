package com.alvarogm.valuebay.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "coins")
public class Coin {

    @Id
    private int lotId;
    private int itemValue;
    private int emissionYear;
    private String conservationStatus;
    private float price;
}
