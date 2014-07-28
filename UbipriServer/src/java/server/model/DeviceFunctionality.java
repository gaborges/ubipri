/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

/**
 *
 * @author Guazzelli
 */
public class DeviceFunctionality {

    private Integer deviceId;
    private Integer functionalityId;

    public DeviceFunctionality() {
    }

    public DeviceFunctionality(Integer deviceId, Integer functionalityId) {
        this.deviceId = deviceId;
        this.functionalityId = functionalityId;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getFunctionalityId() {
        return functionalityId;
    }

    public void setFunctionalityId(Integer functionalityId) {
        this.functionalityId = functionalityId;
    }

}
