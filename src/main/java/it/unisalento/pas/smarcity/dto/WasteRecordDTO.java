package it.unisalento.pas.smarcity.dto;

import java.io.Serializable;

public class WasteRecordDTO implements Serializable {
    private String id;
    private String userUsername;
    private String waste;
    private String wasteTypeDeclared;
    private String wasteTypeEffective;
    private String timeStamp;
    private String binLocation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getWaste() {
        return waste;
    }

    public void setWaste(String waste) {
        this.waste = waste;
    }

    public String getWasteTypeDeclared() {
        return wasteTypeDeclared;
    }

    public void setWasteTypeDeclared(String wasteTypeDeclared) {
        this.wasteTypeDeclared = wasteTypeDeclared;
    }

    public String getWasteTypeEffective() {
        return wasteTypeEffective;
    }

    public void setWasteTypeEffective(String wasteTypeEffective) {
        this.wasteTypeEffective = wasteTypeEffective;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getBinLocation() {
        return binLocation;
    }

    public void setBinLocation(String binLocation) {
        this.binLocation = binLocation;
    }
}
