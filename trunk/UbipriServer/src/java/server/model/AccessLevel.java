/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.util.ArrayList;

/**
 *
 * @author guilherme
 */
public class AccessLevel {
    private Integer id;
    private Double impactFactor;
    private AccessType accessType;
    private EnvironmentType environmentType;
    private ArrayList<Action> actionsList;
    

    public AccessLevel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getImpactFactor() {
        return impactFactor;
    }

    public void setImpactFactor(Double impactFactor) {
        this.impactFactor = impactFactor;
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    public EnvironmentType getEnvironmentType() {
        return environmentType;
    }

    public void setEnvironmentType(EnvironmentType environmentType) {
        this.environmentType = environmentType;
    }

    public ArrayList<Action> getActionsList() {
        return actionsList;
    }

    public void setActionsList(ArrayList<Action> actionsList) {
        this.actionsList = actionsList;
    }
    
    
}
