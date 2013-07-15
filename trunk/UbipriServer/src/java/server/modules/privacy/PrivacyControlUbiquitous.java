/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.modules.privacy;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import server.dao.AccessLevelDAO;
import server.dao.ActionDAO;
import server.dao.DeviceDAO;
import server.dao.EnvironmentDAO;
import server.dao.UserDAO;
import server.model.AccessLevel;
import server.model.AccessType;
import server.model.Action;
import server.model.Device;
import server.model.DeviceCommunication;
import server.model.Environment;
import server.model.EnvironmentType;
import server.model.Functionality;
import server.model.LogEvent;
import server.model.User;
import server.model.UserEnvironment;
import server.modules.communication.Communication;
import server.util.Config;

/**
 * Colocar como Estático talvez
 *
 * @author Estudo
 */
public class PrivacyControlUbiquitous {

    private Communication communication;
    private UserDAO userDAO;
    private EnvironmentDAO envDAO;
    private ActionDAO actDAO;
    private DeviceDAO devDAO;
    private AccessLevelDAO accLevDAO;

    public PrivacyControlUbiquitous() {
        this.userDAO = new UserDAO();
        this.envDAO = new EnvironmentDAO();
        this.actDAO = new ActionDAO();
        this.devDAO = new DeviceDAO();
        this.accLevDAO = new AccessLevelDAO();
        this.communication = new Communication();
    }

//    public void onChangeCurrentUserLocalization(int environmentId, String userName, String deviceCode) {
//        User user = userDAO.getByUserName(userName, false);
//        Environment env = envDAO.get(environmentId, false);
//        user.setCurrentEnvironment(env);
//        userDAO.updateCurrentEnvironment(user);
//
////        LogEvent log = new LogEvent(deviceCode);
////        log.setEnvironment(env);
////        log.setIdentificationCode(null); -- fazer de um jeito que seja adicionado a comunicação do dispositivo é adicionada na tabela de registros;
//        if (environmentId != -1) {
//            Device dev = devDAO.get(deviceCode);
//            //user.setUserDevices(devDAO.getUserDevices(user)); 
//            // Gambiarra já que o Bruno só está usando 1, pedir para ele colocar nos parâmetros o código do dispositivo
//            ArrayList<Functionality> deviceFunctionalities = devDAO.getDeviceFunctionalities(dev.getId());
//            ArrayList<EnvironmentType> envTypes = actDAO.getEnvironmentTypes(environmentId);
//            // encontra aquele que tem a menor diferença de fator de impácto  do que o do usuário, iterando entr todos os tipos de ambiente
//            // fazer depois a seleção as acções a serem enviadas
//            ArrayList<Action> actions = actDAO.getList(env, envTypes.get(0));
//
//            // fazer o código em JSON
//            String json = makeJSONActionsToDevice(deviceFunctionalities, actions);
//            // Tornar escalável - ainda não está, fazer filtro pela comunicação prefered
//            communication.sendActionsToDevice(dev.getDeviceCommunications().get(0), json);
//        }
//    }
//
//    public boolean onChangeDeviceAddress(String deviceCode, int communicationId, String newAddress, String newPort) {
//        DeviceCommunication devComm = new DeviceCommunication();
//        //DeviceCommunicationDAO devCommDAO = new DeviceCommunicationDAO(); // implementar depois
//        // devComm = devCommDAO.get(deviceCode,communicationId);
//        devComm.setAddress(newAddress);
//        devComm.setPort(newPort);
//        // devCommDAO.update(devComm);
//        return true;
//    }
//
//
//    public String makeJSONActionsToDevice(ArrayList<Functionality> deviceFunctionalities, ArrayList<Action> actions) {
//        String json = "{[\n";
//        int j = 0, i = 0;
//        for (i = 0; i < actions.size(); i++) {
//            for (j = 0; j < deviceFunctionalities.size(); j++) {
//                if (deviceFunctionalities.get(j).getId() == actions.get(i).getFunctionality().getId()) {
//                    json += "{fid:" + actions.get(i).getFunctionality().getId() + ",\naction:" + actions.get(i).getAction() + "}\n";
//                }
//            }
//        }
//        json += "]}";
//        return json;
//    }
//
//    public void onChangeCurrentUserLocalization(LogEvent log) {
//        User user = log.getIdentificationCode().getUser();
//        user.setCurrentEnvironment(log.getEnvironment());
//        userDAO.updateCurrentEnvironment(user);
//        if (log.getEnvironment().getId() != -1) {
//            Device dev = devDAO.get(log.getCurrentData()); // pesquisa se o código pertence a algum usuário
//            if (dev == null) {
//                dev = devDAO.getUserDevices(user).get(0); // Gambiarra temporária, adicionar um campo de localização também do dispositivo para tanto
//            }
//            //user.setUserDevices(devDAO.getUserDevices(user)); 
//            // Gambiarra já que o Bruno só está usando 1, pedir para ele colocar nos parâmetros o código do dispositivo
//            ArrayList<Functionality> deviceFunctionalities = devDAO.getDeviceFunctionalities(dev.getId());
//            ArrayList<EnvironmentType> envTypes = actDAO.getEnvironmentTypes(log.getEnvironment().getId());
//            // encontra aquele que tem a menor diferença de fator de impácto  do que o do usuário, iterando entr todos os tipos de ambiente
//            // fazer depois a seleção as acções a serem enviadas
//            ArrayList<Action> actions = actDAO.getList(log.getEnvironment(), envTypes.get(0));
//
//            // fazer o código em JSON
//            String json = makeJSONActionsToDevice(deviceFunctionalities, actions);
//            // Tornar escalável - ainda não está, fazer filtro pela comunicação prefered
//            communication.sendActionsToDevice(dev.getDeviceCommunications().get(0), json);
//        }
//    }
//
//    public String onChangeCurrentUserLocalizationReturnJson(int environmentId, String userName, String deviceCode) {
//        User user = userDAO.getByUserName(userName, false);
//        Environment env = envDAO.get(environmentId, false);
//        String json = null;
//        user.setCurrentEnvironment(env);
//        userDAO.updateCurrentEnvironment(user);
//        if (environmentId != -1) {
//            Device dev = devDAO.get(deviceCode);
//            //user.setUserDevices(devDAO.getUserDevices(user)); 
//            // Gambiarra já que o Bruno só está usando 1, pedir para ele colocar nos parâmetros o código do dispositivo
//            ArrayList<Functionality> deviceFunctionalities = devDAO.getDeviceFunctionalities(dev.getId());
//            ArrayList<EnvironmentType> envTypes = actDAO.getEnvironmentTypes(environmentId);
//            // encontra aquele que tem a menor diferença de fator de impácto  do que o do usuário, iterando entr todos os tipos de ambiente
//            // fazer depois a seleção as acções a serem enviadas
//            ArrayList<Action> actions = actDAO.getList(env, envTypes.get(0));
//
//            // fazer o código em JSON
//            json = makeJSONActionsToDevice(deviceFunctionalities, actions);
//            // Tornar escalável - ainda não está, fazer filtro pela comunicação prefered
//        }
//        return json;
//    }
    public Communication getCommunication() {
        return communication;
    }

    public void setCommunication(Communication communication) {
        this.communication = communication;
    }

    public boolean onChangeCurrentUserLocalization(int environmentId, String userName, String userPassword, String deviceCode) {
        return false; // falta implementar
    }

    public String onChangeCurrentUserLocalizationWithReturnAsynchronousActions(int environmentId, String userName, String userPassword, String deviceCode) {
        // Verifica login e senha do usuário, se sim retorna o status OK e continua senão retorna Status DENY 
        if (!this.userHasAccessPermission(userName, userPassword)) {
            return "[{\"status\":\"DENY\"}]"; // em breve fazer mensagens dinâmicas (Existem protocolos Diferentes)
        }
        boolean hasChandedUserLocation = this.hasChangedUserEnvironment(userName, environmentId);
        boolean hasChangedDeviceLocation = this.hasChangedDeviceEnvironment(deviceCode, environmentId);
        // se nem o dispositivo nem o usuário mudaram de localização então não é necessário seguir em frente
        if (!hasChandedUserLocation && !hasChangedDeviceLocation) {
            return "[{\"status\":\"OK\"}]";
        }
        // Verifica se o usuário continua no mesmo ambiente, se sim continua, senão retorna status OK
        if (hasChandedUserLocation) {
            this.updateUserEnvironment(userName, environmentId); // em breve fazer mensagens dinâmicas (Existem protocolocos Diferentes)
        }
        // Atualiza localização do Usuáriodo Dispositivo
        if (hasChangedDeviceLocation) {
            this.updateDeviceEnvironment(deviceCode, environmentId);
        }
        // Busca Usuário no Ambiente e Device
        Device dev = this.devDAO.get(deviceCode);
        // Busca Tipo de ambiente - dentro do usuário
        // Busca Busca o tipo de acesso do usuário no ambiente
        User user = this.userDAO.getUserEnvironment(userName, environmentId);
        // Salva Log de acesso (Contexto de Localização)
        LogEvent eve = new LogEvent();
        eve.setDevice(dev);
        eve.setTime(new Date());
        eve.setEnvironment(user.getUsersEnvironment().getEnvironment());
        eve.setEvent("Localization");
        eve.setUser(user);
        envDAO.insertLog(eve);
        // Função de Busca de ações
        // Busca Tipo de ambiente
        // Busca Busca o tipo de acesso do usuário no ambiente
        // Busca o nível de acesso do usuário e ações default
        // Busca se há ações customizadas
        // Sobrepoem as ações Default pelas customizadas
        ArrayList<Action> actions = this.getListActions(user, dev);
        // A partir de um Array de ações, gera o Json das ações {-- Falta a questão do tipo de comunicação a ser resolvida}
        // adiciona o status OK e o json das ações em uma única mensagem
        makeAndSendMessage(dev.getPreferredDeviceCommunication(), actions); // Em breve enviar todas as comunicações
        // retorna mensagem
        return "[{\"status\":\"OK\"}]";
    }

    public String onChangeCurrentUserLocalizationReturnActions(int environmentId, String userName, String userPassword, String deviceCode) {
        // Verifica login e senha do usuário, se sim retorna o status OK e continua senão retorna Status DENY 
        if (!this.userHasAccessPermission(userName, userPassword)) {
            return "[{\"status\":\"DENY\"}]"; // em breve fazer mensagens dinâmicas (Existem protocolos Diferentes)
        }
        boolean hasChandedUserLocation = this.hasChangedUserEnvironment(userName, environmentId);
        boolean hasChangedDeviceLocation = this.hasChangedDeviceEnvironment(deviceCode, environmentId);
        // se nem o dispositivo nem o usuário mudaram de localização então não é necessário seguir em frente
        if (!hasChandedUserLocation && !hasChangedDeviceLocation) {
            return "[{\"status\":\"OK\"}]";
        }
        // Verifica se o usuário continua no mesmo ambiente, se sim continua, senão retorna status OK
        if (hasChandedUserLocation) {
            this.updateUserEnvironment(userName, environmentId); // em breve fazer mensagens dinâmicas (Existem protocolocos Diferentes)
        }
        // Atualiza localização do Usuáriodo Dispositivo
        if (hasChangedDeviceLocation) {
            this.updateDeviceEnvironment(deviceCode, environmentId);
        }
        // Busca Usuário no Ambiente e Device
        Device dev = this.devDAO.get(deviceCode);
        dev.setListFunctionalities(devDAO.getDeviceFunctionalities(dev.getId()));
        // Busca Tipo de ambiente - dentro do usuário
        // Busca Busca o tipo de acesso do usuário no ambiente
        User user = this.userDAO.getUserEnvironment(userName, environmentId);
        // Salva Log de acesso (Contexto de Localização)
        LogEvent eve = new LogEvent();
        eve.setDevice(dev);
        eve.setTime(new Date());
        eve.setEnvironment(user.getUsersEnvironment().getEnvironment());
        eve.setEvent("Localization");
        eve.setUser(user);
        envDAO.insertLog(eve);
        // Função de Busca de ações
        // Busca Tipo de ambiente
        // Busca Busca o tipo de acesso do usuário no ambiente
        // Busca o nível de acesso do usuário e ações default
        // Busca se há ações customizadas
        // Sobrepoem as ações Default pelas customizadas
        ArrayList<Action> actions = this.getListActions(user, dev);
        // A partir de um Array de ações, gera o Json das ações
        // adiciona o status OK e o json das ações em uma única mensagem
        // envia por chamada assincrona
        return makeMessage(actions);
    }

    // Verifica login e senha do usuário, se sim retorna o status OK e continua senão retorna Status DENY 
    private boolean userHasAccessPermission(String userName, String userPassword) {
        return this.userDAO.hasAccessPermission(userName, userPassword);
    }

    // Verifica se o usuário continua no mesmo ambiente, se sim continua, senão retorna status OK
    private boolean hasChangedUserEnvironment(String userName, int environmentId) {
        return this.userDAO.hasChangedUserEnvironment(userName, environmentId);
    }

    // Atualiza localização do Usuário e do Dispositivo
    private void updateUserEnvironment(String userName, int environment) {
        this.userDAO.updateCurrentEnvironment(userName, environment);
    }

    // Atualiza localização do Dispositivo
    private void updateDeviceEnvironment(String deviceCode, int environmentId) {
        this.devDAO.updateCurrentEnvironment(deviceCode, environmentId);
    }

    // Função de Busca de ações
    private ArrayList<Action> getListActions(User userEnvironment, Device device) {
        int i, j;
        // Busca o nível de acesso do usuário e ações default
        AccessLevel accessLevel = this.accLevDAO.get(
                userEnvironment.getUsersEnvironment().getEnvironment().getEnvironmentType(),
                userEnvironment.getUsersEnvironment().getCurrentAccessType());
        ArrayList<Action> finalActions = new ArrayList<Action>(),
                customActions = this.actDAO.getCustomActions(
                userEnvironment.getUsersEnvironment().getEnvironment(), accessLevel);

        // Sobrepoem as ações Default pelas customizadas
        if (customActions.size() > 0) {
            for (i = 0; i < accessLevel.getActionsList().size(); i++) {
                for (j = 0; j < customActions.size(); j++) {
                    if (accessLevel.getActionsList().get(i).getId() == customActions.get(j).getId()) {
                        accessLevel.getActionsList().set(i, customActions.get(j));
                    }
                }
            }
        }

        // Filtra pelo número de funcionalidades que o device possui    
        for (i = 0; i < accessLevel.getActionsList().size(); i++) {
            for (j = 0; j < device.getListFunctionalities().size(); j++) {
                // System.out.println("De");
                if (device.getListFunctionalities().get(j).getId() == accessLevel.getActionsList().get(i).getFunctionality().getId()) {
                    finalActions.add(accessLevel.getActionsList().get(i));
                }
            }
        }
        //System.out.println("TAM L.D.: "+accessLevel.getActionsList().size()+",L.C.:"+customActions.size()+",L.F.:"+accessLevel.getActionsList().size()+",D.Func.:"+device.getListFunctionalities().size());
        return finalActions;
    }

    // Função de Busca de ações
    private ArrayList<Action> getListActions(int environmentId, String userName, String deviceCode) {
        int j = 0, i = 0;
        Device dev = this.devDAO.get(deviceCode);
        // Busca Tipo de ambiente - dentro do usuário
        // Busca Busca o tipo de acesso do usuário no ambiente
        User user = this.userDAO.getUserEnvironment(userName, environmentId);
        // Busca o nível de acesso do usuário e ações default
        AccessLevel nivelAcesso = this.accLevDAO.get(
                user.getCurrentEnvironment().getEnvironmentType(),
                user.getUsersEnvironment().getCurrentAccessType());
        // Busca se há ações customizadas
        ArrayList<Action> finalActions = new ArrayList<Action>(), customActions = this.actDAO.getCustomActions(environmentId, nivelAcesso.getId());
        // Sobrepoem as ações Default pelas customizadas
        if (customActions.size() > 0) {
            for (i = 0; i < nivelAcesso.getActionsList().size(); i++) {
                for (j = 0; j < customActions.size(); j++) {
                    if (nivelAcesso.getActionsList().get(i).getId() == customActions.get(j).getId()) {
                        nivelAcesso.getActionsList().set(i, customActions.get(j));
                    }
                }
            }
        }

        // Filtra pelo número de funcionalidades que o device possui    
        for (i = 0; i < nivelAcesso.getActionsList().size(); i++) {
            for (j = 0; j < dev.getListFunctionalities().size(); j++) {
                if (dev.getListFunctionalities().get(j).getId() == nivelAcesso.getActionsList().get(i).getFunctionality().getId()) {
                    finalActions.add(nivelAcesso.getActionsList().get(i));
                }
            }
        }
        return finalActions;
    }

    private boolean makeAndSendMessage(DeviceCommunication deviceCommunication, ArrayList<Action> actions) {
        if (this.communication.sendActionsToDevice(deviceCommunication, makeMessage(actions))) {
            return true;
        } else {
            return false;
        }
    }

    private String makeMessage(ArrayList<Action> actions) {
        return this.makeMessage(actions, "OK");
    }

    private String makeMessage(ArrayList<Action> actions, String status) {
        String json = "{\"status\":\"" + status + "\",\"Actions\":[";
        for (int i = 0; i < actions.size();i++) {
            json += "{\"fid\":" + actions.get(i).getFunctionality().getId() 
                    + ",\"action\":\"" + actions.get(i).getAction() + "\"}";
            if(i != (actions.size()-1)) json += ",";
        }
        return json + "]}";
    }
    //private String messageActionsJSON(ArrayList<Action>, status)

    public void onChangeCurrentUserLocalization(int environmentId, String deviceCode) {
        User user;
        // Verifica se dispositivo existe
        if (this.isDeviceRegistered(deviceCode)) {
            user = this.userDAO.getUserByDeviceCode(deviceCode);
            
            boolean hasChandedUserLocation = this.hasChangedUserEnvironment(user.getUserName(), environmentId);
            boolean hasChangedDeviceLocation = this.hasChangedDeviceEnvironment(deviceCode, environmentId);
      
            if(Config.debugPrivacyModule)System.out.println("Usuário no Ambiente: "+hasChandedUserLocation);
            if(Config.debugPrivacyModule)System.out.println("Dispositivo no Ambiente: "+hasChangedDeviceLocation);
            // se nem o dispositivo nem o usuário mudaram de localização então não é necessário seguir em frente
            if (!hasChandedUserLocation && !hasChangedDeviceLocation) {
                return;
            }
            // Verifica se o usuário continua no mesmo ambiente
            if (hasChandedUserLocation) {
                this.updateUserEnvironment(user.getUserName(), environmentId); // em breve fazer mensagens dinâmicas (Existem protocolocos Diferentes)
            }
            // Verifica se o device mudou de ambiente e atualiza localização do Dispositivo
            if (hasChangedDeviceLocation) {
                this.updateDeviceEnvironment(deviceCode, environmentId);
            }
        } else {
            return;
        }

        // Busca Usuário no Ambiente e Device
        Device dev = this.devDAO.get(deviceCode);
        // Busca Tipo de ambiente - dentro do usuário
        // Busca Busca o tipo de acesso do usuário no ambiente
        user = this.userDAO.getUserEnvironment(user.getUserName(), environmentId);
        // Salva Log de acesso (Contexto de Localização)
        LogEvent eve = new LogEvent();
        eve.setDevice(dev);
        eve.setTime(new Date());
        eve.setEnvironment(user.getUsersEnvironment().getEnvironment());
        eve.setEvent("Localization");
        eve.setUser(user);
        envDAO.insertLog(eve);

    }

    private boolean isDeviceRegistered(String deviceCode) {
        return this.devDAO.isDeviceRegistered(deviceCode);
    }

    private boolean hasChangedDeviceEnvironment(String deviceCode, int environmentId) {
        return this.devDAO.hasChangedDeviceEnvironment(deviceCode, environmentId);
    }
}
