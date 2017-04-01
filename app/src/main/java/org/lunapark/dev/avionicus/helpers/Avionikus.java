package org.lunapark.dev.avionicus.helpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Avionikus {

    private List<Object> aWaypoints = null;
    private ATrack aTrack;
    private List<List<Double>> aPoints = null;
    private String sMsg;
    private String sMsgTitle;
    private Boolean bStateError;
    private Object minId;
    private Map<String, String> additionalProperties = new HashMap<String, String>();

    public List<Object> getAWaypoints() {
        return aWaypoints;
    }

    public void setAWaypoints(List<Object> aWaypoints) {
        this.aWaypoints = aWaypoints;
    }

    public ATrack getATrack() {
        return aTrack;
    }

    public void setATrack(ATrack aTrack) {
        this.aTrack = aTrack;
    }

    public List<List<Double>> getAPoints() {
        return aPoints;
    }

    public void setAPoints(List<List<Double>> aPoints) {
        this.aPoints = aPoints;
    }

    public String getSMsg() {
        return sMsg;
    }

    public void setSMsg(String sMsg) {
        this.sMsg = sMsg;
    }

    public String getSMsgTitle() {
        return sMsgTitle;
    }

    public void setSMsgTitle(String sMsgTitle) {
        this.sMsgTitle = sMsgTitle;
    }

    public Boolean getBStateError() {
        return bStateError;
    }

    public void setBStateError(Boolean bStateError) {
        this.bStateError = bStateError;
    }

    public Object getMinId() {
        return minId;
    }

    public void setMinId(Object minId) {
        this.minId = minId;
    }

    public Map<String, String> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, String value) {
        this.additionalProperties.put(name, value);
    }

}
