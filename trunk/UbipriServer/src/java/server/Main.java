/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import server.dao.EnvironmentDAO;
import server.modules.communication.Communication;
import server.modules.privacy.PrivacyControlUbiquitous;
import server.modules.service.EnvironmentService;

/**
 *
 * @author Estudo
 */
public class Main {
    public static void main(String[] args) {
        Communication comm = new Communication();
        PrivacyControlUbiquitous ubipri =  new PrivacyControlUbiquitous();
        EnvironmentDAO dao = new EnvironmentDAO();
        EnvironmentService service = new EnvironmentService(dao.get(1, false), ubipri);
        service.start();
        
        while(true){}
    }
    
}
