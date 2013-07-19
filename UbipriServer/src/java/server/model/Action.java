/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.util.Date;

/**
 *
 * @author Estudo
 */
public class Action {
    private Integer id;
    private AccessLevel accessLevel;
    private Functionality functionality;
    private Environment environment; // Caso o ambiente seja customizado
    private String action;
    private Date startDate;
    private Date endDate;
    private Integer startDailyInterval;
    private Integer endDailyInterval;
    

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public Functionality getFunctionality() {
        return functionality;
    }

    public void setFunctionality(Functionality functionality) {
        this.functionality = functionality;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getStartDailyInterval() {
        return startDailyInterval;
    }

    public void setStartDailyInterval(Integer startDailyInterval) {
        this.startDailyInterval = startDailyInterval;
    }

    public Integer getEndDailyInterval() {
        return endDailyInterval;
    }

    public void setEndDailyInterval(Integer endDailyInterval) {
        this.endDailyInterval = endDailyInterval;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
        
}