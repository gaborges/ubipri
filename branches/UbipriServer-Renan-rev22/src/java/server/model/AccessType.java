/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

/**
 *
 * @author Estudo
 */
public class AccessType {
    
    public static final String BLOCKED = "Blocked";
    public static final String GUEST = "Guest";
    public static final String BASIC = "Basic";
    public static final String ADVANCED = "Advanced";
    public static final String ADMINISTRATIVE = "Administrative";
    
    public static final Integer BLOCKED_ID = 1;
    public static final Integer GUEST_ID = 2;
    public static final Integer BASIC_ID = 3;
    public static final Integer ADVANCED_ID = 4;
    public static final Integer ADMINISTRATIVE_ID = 5;
    
    private Integer id;
    private String name;

    public AccessType() {
    }

    public AccessType(Integer id, String name) {
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
