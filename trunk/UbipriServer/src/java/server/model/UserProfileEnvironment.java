/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.model;

/**
 *
 * @author Borges
 */
public class UserProfileEnvironment {
    /*  Automatically classified IDs of the Default User Profiles in Environment */
    public static final Integer UNKNOW = 1;
    public static final Integer TRANSIENT = 2;
    public static final Integer USER = 3;
        
    private Integer id;
    private String name;

    public UserProfileEnvironment(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserProfileEnvironment() {
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
