/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.modules.communication;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author guilherme
 */
@XmlRootElement
public class InsertCommunicationCodeParameters implements Serializable{
    private String userName;
    private String userPassword;
    private String deviceCode;
    private String communicationCode;
    private int communicationType;
    private int communicationId;
    private String deviceName;
    private int[] functionalities;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getCommunicationCode() {
        return communicationCode;
    }

    public void setCommunicationCode(String communicationCode) {
        this.communicationCode = communicationCode;
    }

    public int getCommunicationType() {
        return communicationType;
    }

    public void setCommunicationType(int communicationType) {
        this.communicationType = communicationType;
    }

    public int getCommunicationId() {
        return communicationId;
    }

    public void setCommunicationId(int communicationId) {
        this.communicationId = communicationId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int[] getFunctionalities() {
        return functionalities;
    }

    public void setFunctionalities(int[] functionalities) {
        this.functionalities = functionalities;
    }

}
