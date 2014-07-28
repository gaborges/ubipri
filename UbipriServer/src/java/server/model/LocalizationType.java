/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

/**
 *
 * @author Estudo
 */
public class LocalizationType {
    private Integer id;
    private String name;
    private Double precision;
    private String metric;

    public LocalizationType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public LocalizationType(Integer id) {
        this.id = id;
    }

    public LocalizationType(Integer id, String name, Double precision, String metric) {
        this.id = id;
        this.name = name;
        this.precision = precision;
        this.metric = metric;
    }

    public LocalizationType() {
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

    public Double getPrecision() {
        return precision;
    }

    public void setPrecision(Double precision) {
        this.precision = precision;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }
    
}
