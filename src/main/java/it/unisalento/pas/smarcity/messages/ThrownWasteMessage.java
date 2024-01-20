package it.unisalento.pas.smarcity.messages;

import java.io.Serializable;

public class ThrownWasteMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;
    private String wasteType;
    private String waste;
    private String binId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWasteType() {
        return wasteType;
    }

    public void setWasteType(String wasteType) {
        this.wasteType = wasteType;
    }

    public String getBinId() {
        return binId;
    }

    public void setBinId(String binId) {
        this.binId = binId;
    }

    public String getWaste() {
        return waste;
    }

    public void setWaste(String waste) {
        this.waste = waste;
    }
}
