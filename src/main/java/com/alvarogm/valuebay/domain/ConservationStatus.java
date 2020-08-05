package com.alvarogm.valuebay.domain;

public enum ConservationStatus {
    BC("Buena conservación"),
    MBC("Muy buena conservación"),
    EBC("Excelente buena conservación"),
    SC("Sin circular");

    private final String detailedStatus;

    ConservationStatus(String detailedStatus){
        this.detailedStatus = detailedStatus;
    }

    public String getDetailedStatus() {
        return detailedStatus;
    }
}
