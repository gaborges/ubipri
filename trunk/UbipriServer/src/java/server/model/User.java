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
public class User {
    
    private Integer id;
    private String userName;
    private String password;
    private String fullName;
    private Environment currentEnvironment;
    private UserEnvironment usersEnvironment; // - Implementar em breve
    private ArrayList<Device> userDevices;
    private UserType userType;

    public User(Integer id, String userName, String password, String fullName) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
    }
    
    public User(){
        this.userDevices = new ArrayList<Device>();
        // this.usersEnvironment = new ArrayList<UserEnvironment>();
    }

    public User(String userName, String password) {
        this();
        this.userName = userName;
        this.password = password;
    }

    public User(Integer id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Environment getCurrentEnvironment() {
        return currentEnvironment;
    }

    public void setCurrentEnvironment(Environment currentEnvironment) {
        this.currentEnvironment = currentEnvironment;
    }

    public ArrayList<Device> getUserDevices() {
        return userDevices;
    }

    public void setUserDevices(ArrayList<Device> userDevices) {
        this.userDevices = userDevices;
    }

    public UserEnvironment getUsersEnvironment() {
        return usersEnvironment;
    }

    public void setUsersEnvironment(UserEnvironment usersEnvironment) {
        this.usersEnvironment = usersEnvironment;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    
}
