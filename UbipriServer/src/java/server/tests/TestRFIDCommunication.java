/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.tests;

import server.dao.EnvironmentDAO;
import server.model.Environment;
import server.modules.privacy.PrivacyControlUbiquitous;
import server.modules.service.EnvironmentService;

/**
 *
 * @author Estudo
 */
public class TestRFIDCommunication {
    public static void main(String[] args) {
        EnvironmentDAO envDAO = new EnvironmentDAO();
        PrivacyControlUbiquitous privacyModule = new PrivacyControlUbiquitous();
        Environment env = envDAO.get(2, true);
        EnvironmentService service = new EnvironmentService(env, privacyModule);
        
        service.newReading("222");
        service.newReading("5E-EF-8-9B-54");
    }
}
