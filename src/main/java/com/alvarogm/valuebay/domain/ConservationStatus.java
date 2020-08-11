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


    public static String translateConservationStatus(String rawConservationStatus){
        switch (rawConservationStatus){

            case "BC"   :   return ConservationStatus.BC.getDetailedStatus();
            case "MBC"  :   return ConservationStatus.MBC.getDetailedStatus();
            case "EBC"  :   return ConservationStatus.EBC.getDetailedStatus();
            case "SC"   :   return ConservationStatus.SC.getDetailedStatus();
            default     :   return null;
        }
    }


    public static boolean isValidConservationStatus(String rawConservationStatus){

        return "bc".equals(rawConservationStatus.toLowerCase()) || "mbc".equals(rawConservationStatus.toLowerCase()) ||
                "ebc".equals(rawConservationStatus.toLowerCase()) || "sc".equals(rawConservationStatus.toLowerCase());

    }
}
