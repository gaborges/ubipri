/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

/**
 *
 * @author Estudo
 */
public class IdentificationCode {
    private Integer id;
    private User user;
    private LocalizationType localizationType;
    private String code;

    public IdentificationCode() {
        id = -1;
    }

    public IdentificationCode(String code){
        this();
        this.code = code;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalizationType getLocalizationType() {
        return localizationType;
    }

    public void setLocalizationType(LocalizationType localizationType) {
        this.localizationType = localizationType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
}
