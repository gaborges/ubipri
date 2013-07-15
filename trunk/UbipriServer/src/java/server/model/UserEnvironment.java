/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

/**
 *
 * @author Estudo
 */
public class UserEnvironment {
    private Integer id;
    private Environment environment;
    private double impactFactor;
    private AccessType currentAccessType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public double getImpactFactor() {
        return impactFactor;
    }

    public void setImpactFactor(double impactFactor) {
        this.impactFactor = impactFactor;
    }

    public AccessType getCurrentAccessType() {
        return currentAccessType;
    }

    public void setCurrentAccessType(AccessType currentAccessType) {
        this.currentAccessType = currentAccessType;
    }
    
    
    
}
