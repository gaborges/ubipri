/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.util.ArrayList;

/**
 *
 * @author Estudo
 */
public class Environment {
    private Integer id;
    private String name;
    private Double longitude;
    private Double latitude;
    private LocalizationType localizationType;
    private EnvironmentType environmentType;
    private Environment parentEnvironment;
    private ArrayList<Action> environmentcustomActions;

    public Environment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public LocalizationType getLocalizationType() {
        return localizationType;
    }

    public void setLocalizationType(LocalizationType localizationType) {
        this.localizationType = localizationType;
    }

    public Environment getParentEnvironment() {
        return parentEnvironment;
    }

    public void setParentEnvironment(Environment parentEnvironment) {
        this.parentEnvironment = parentEnvironment;
    }

    public EnvironmentType getEnvironmentType() {
        return environmentType;
    }

    public void setEnvironmentType(EnvironmentType environmentType) {
        this.environmentType = environmentType;
    }

    public ArrayList<Action> getEnvironmentcustomActions() {
        return environmentcustomActions;
    }

    public void setEnvironmentcustomActions(ArrayList<Action> environmentcustomActions) {
        this.environmentcustomActions = environmentcustomActions;
    }

}