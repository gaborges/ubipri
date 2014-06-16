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
public class GetEnvironmentParameters implements Serializable {
    private String userName;
    private String userPassword;
    private String deviceCode;
    private int currentVersion;
    private double latitude;
    private double longitude;
    private String simbolocBaseEnvironment;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public int getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(int currentVersion) {
        this.currentVersion = currentVersion;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getSimbolocBaseEnvironment() {
        return simbolocBaseEnvironment;
    }

    public void setSimbolocBaseEnvironment(String simbolocBaseEnvironment) {
        this.simbolocBaseEnvironment = simbolocBaseEnvironment;
    }

    @Override
    public String toString() {
        return "GetEnvironmentParametens{" + "userName=" + userName + ", userPassword=" + userPassword + ", deviceCode=" + deviceCode + ", currentVersion=" + currentVersion + ", latitude=" + latitude + ", longitude=" + longitude + ", simbolocBaseEnvironment=" + simbolocBaseEnvironment + '}';
    }
  
}
