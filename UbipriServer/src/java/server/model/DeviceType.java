/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

/**
 *
 * @author Estudo
 */
public class DeviceType {
    private Integer id;
    private String name;

    public DeviceType() {
    }

    public DeviceType(Integer id, String name) {
        this.id = id;
        this.name = name;
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
    
}
