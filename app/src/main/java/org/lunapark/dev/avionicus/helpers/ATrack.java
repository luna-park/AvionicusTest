package org.lunapark.dev.avionicus.helpers;

import java.util.HashMap;
import java.util.Map;

public class ATrack {

    private String type;
    private String dtStart;
    private String dtEnd;
    private String time;
    private String distance;
    private String idTrack;
    private String spAvg;
    private String spMax;
    private String calories;
    private Object description;
    private String access;
    private String weight;
    private String cardio;
    private Integer hrMax;
    private Integer hrAvg;
    private String varMax;
    private String varMin;
    private Boolean status;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDtStart() {
        return dtStart;
    }

    public void setDtStart(String dtStart) {
        this.dtStart = dtStart;
    }

    public String getDtEnd() {
        return dtEnd;
    }

    public void setDtEnd(String dtEnd) {
        this.dtEnd = dtEnd;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getIdTrack() {
        return idTrack;
    }

    public void setIdTrack(String idTrack) {
        this.idTrack = idTrack;
    }

    public String getSpAvg() {
        return spAvg;
    }

    public void setSpAvg(String spAvg) {
        this.spAvg = spAvg;
    }

    public String getSpMax() {
        return spMax;
    }

    public void setSpMax(String spMax) {
        this.spMax = spMax;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getCardio() {
        return cardio;
    }

    public void setCardio(String cardio) {
        this.cardio = cardio;
    }

    public Integer getHrMax() {
        return hrMax;
    }

    public void setHrMax(Integer hrMax) {
        this.hrMax = hrMax;
    }

    public Integer getHrAvg() {
        return hrAvg;
    }

    public void setHrAvg(Integer hrAvg) {
        this.hrAvg = hrAvg;
    }

    public String getVarMax() {
        return varMax;
    }

    public void setVarMax(String varMax) {
        this.varMax = varMax;
    }

    public String getVarMin() {
        return varMin;
    }

    public void setVarMin(String varMin) {
        this.varMin = varMin;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
