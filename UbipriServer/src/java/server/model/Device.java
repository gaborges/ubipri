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
public class Device {
    private Integer id;
    private String code;
    private String name;
    private DeviceType deviceType;
    private Environment currentEnvironment;
    private ArrayList<Functionality> listFunctionalities;
    private ArrayList<DeviceCommunication> deviceCommunications;
    private User user;
    
    public Device() {
        this.listFunctionalities = new ArrayList<Functionality>();
        this.deviceCommunications = new ArrayList<DeviceCommunication>();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<DeviceCommunication> getDeviceCommunications() {
        return deviceCommunications;
    }

    public void setDeviceCommunications(ArrayList<DeviceCommunication> deviceCommunications) {
        this.deviceCommunications = deviceCommunications;
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

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public ArrayList<Functionality> getListFunctionalities() {
        return listFunctionalities;
    }

    public void setListFunctionalities(ArrayList<Functionality> listFunctionalitys) {
        this.listFunctionalities = listFunctionalitys;
    }

    public Environment getCurrentEnvironment() {
        return currentEnvironment;
    }

    public void setCurrentEnvironment(Environment currentEnvironment) {
        this.currentEnvironment = currentEnvironment;
    }

    public DeviceCommunication getPreferredDeviceCommunication() {
        int i, preferredIndex = deviceCommunications.size(), 
                preferedOrder = 0;
        
        for(i=0; i < this.deviceCommunications.size();i++){
            if(preferredIndex > this.deviceCommunications.get(i).getPreferredOrder()){
                preferredIndex = this.deviceCommunications.get(i).getPreferredOrder();
                preferedOrder = i;
            }
        }
        System.out.println("Achou: "+preferredIndex +" com: ");
        return this.deviceCommunications.get(preferedOrder);
    }
    
    
}
