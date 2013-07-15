/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.modules.service;

import server.dao.EnvironmentDAO;
import server.model.Environment;
import server.model.IdentificationCode;
import server.model.LogEvent;
import server.modules.abstraction.Interaction;
import server.modules.localcommunication.LocalCommunicationSerialUSB;
import server.modules.privacy.PrivacyControlUbiquitous;
import server.util.Config;

/**
 *
 * @author Estudo
 */
public class EnvironmentService implements Runnable{
    
    private Environment currentEnvironment;
    private Interaction interaction; // Retirar caso faça o array de dispositivos virtuais
    // private ArrayList<VirtualDevice> environmentVirtualDevices; // Depois fazer para ficar escalável, ter vários dispositivos que representam também sensores 
    private EnvironmentDAO envDAO;
    private PrivacyControlUbiquitous privacyModule;
    private Boolean active;

    public EnvironmentService(Environment currentEnvironment, PrivacyControlUbiquitous privacyModule) {
        this.currentEnvironment = currentEnvironment;
        this.privacyModule = privacyModule;
        this.active = false;
        
        this.envDAO = new EnvironmentDAO();
        LocalCommunicationSerialUSB usb = 
               //new LocalCommunicationSerialUSB("COM14");
               //new LocalCommunicationSerialUSB("COM3"); 
                new LocalCommunicationSerialUSB("/dev/ttyUSB0"); // linux
        // Futuramente passar por parâmetro pelo serviço e criar classes
        // contendo os dados a serem utilizados dinamicamente como comunicação, porta, endereço, descrição do dispositivo, contextos ,etc. 
        this.interaction = new Interaction(this, usb);
        usb.setLocalInteraction(interaction);
        
    } 
    
    public void start(){
        interaction.start();
        active = true;
    }
    
    public void stop(){
        active = false;
    }
    
    public void newReading(String currentData){
        if(Config.debugResourceService) {
            System.out.println("LOG: "+currentData);
        }      
        this.privacyModule.onChangeCurrentUserLocalization(currentEnvironment.getId(), currentData);    
    }

    @Override
    public void run() {
        start();
        while(active){
            // Faz alguma coisa, futuramente checa se os dispositivos estão OK
        }
    }
}
