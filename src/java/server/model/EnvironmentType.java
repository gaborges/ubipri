/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

/**
 *
 * @author Estudo
 */
public class EnvironmentType {
    private Integer id;
    private String name;

    public EnvironmentType() {
    }

    public EnvironmentType(String name) {
        this();
        this.name = name;
    }

    public EnvironmentType(Integer id, String name) {
        this();
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
