/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.modules.privacy;

import java.util.ArrayList;
import java.util.Date;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.dao.AccessLevelDAO;
import server.dao.ActionDAO;
import server.dao.DeviceDAO;
import server.dao.EnvironmentDAO;
import server.dao.UserDAO;
import server.model.AccessLevel;
import server.model.AccessType;
import server.model.Action;
import server.model.Device;
import server.model.DeviceType;
import server.model.Environment;
import server.model.LogEvent;
import server.model.Point;
import server.model.User;
import server.model.classify.Classifier;
import server.model.classify.ClassifierFactory;
import server.modules.communication.Communication;
import server.util.AccessBD;
import server.util.SingleConnection;

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
        AccessBD db = SingleConnection.getAccessDB();
//                new AccessBD(
//                Config.dbServer, // IP do Servidor
//                Config.dbName, // Nome do Banco de dados
//                Config.dbUser, // Usuário
//                Config.dbPassword); // Senha
        this.userDAO = new UserDAO(db);
        this.envDAO = new EnvironmentDAO(db);
        this.actDAO = new ActionDAO(db);
        this.devDAO = new DeviceDAO(db);
        this.accLevDAO = new AccessLevelDAO(db);
        this.communication = new Communication();
    }

    public Communication getCommunication() {
        return communication;
    }

    public void setCommunication(Communication communication) {
        this.communication = communication;
    }

    public String onChangeCurrentUserLocalizationWithReturnAsynchronousActions(int environmentId, String userName, String userPassword, String deviceCode,Boolean exiting) {
        // Verifica login e senha do usuário conferem acesso ao serviço, se sim retorna o status OK e continua senão retorna Status DENY; 
        if (!this.userHasAccessPermission(userName, userPassword)) {
            return "{\"status\":\"DENY\"}"; // em breve fazer mensagens dinâmicas (Existem protocolos Diferentes)
        }
        // Verifica se o dispositivo está cadastrado, se não estiver retorna ERROR;
        if(!this.devDAO.isDeviceRegistered(deviceCode)){
            return "{\"status\":\"ERROR\"}"; 
        }
            // Funções para certificar que o usuário e o dispositivo mudaram de local -- Comentei pois não tratei a entrada e a saída, somente um caso
        //boolean hasChandedUserLocation = this.hasChangedUserEnvironment(userName, environmentId);
       // boolean hasChangedDeviceLocation = this.hasChangedDeviceEnvironment(deviceCode, environmentId);
        // upar novamente --
        // se nem o dispositivo nem o usuário mudaram de localização então não é necessário seguir em frente
        //if (!hasChandedUserLocation && !hasChangedDeviceLocation) {
        //    return "{\"status\":\"OK\"}";
        // }
        // Verifica se o usuário continua no mesmo ambiente, se sim continua, senão retorna status OK
        //if (hasChandedUserLocation) {
        //    this.updateUserEnvironment(userName, environmentId); // em breve fazer mensagens dinâmicas (Existem protocolocos Diferentes)
        //}
        // Atualiza localização do Usuáriodo Dispositivo
        // if (hasChangedDeviceLocation) {
        //    this.updateDeviceEnvironment(deviceCode, environmentId);
        //}
        
        // Busca Usuário no Ambiente e Device
        Device dev = this.devDAO.get(deviceCode);
            // Busca as funcionalidades do Device
        dev.setListFunctionalities(devDAO.getDeviceFunctionalities(dev.getId()));
        // Busca Tipo de ambiente e o tipo de acesso do usuário no ambiente;
        User user = this.userDAO.getUserEnvironment(userName, environmentId);
        // Cria e salva Log de acesso (Contexto de Localização);
        LogEvent eve = new LogEvent();
        eve.setDevice(dev);
        eve.setTime(new Date());
        eve.setEnvironment(user.getUsersEnvironment().getEnvironment());
        eve.setEvent((exiting)? "Leaving the location":"Entering the location");
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
        // Envia ao dispositivo uma mensagem contendo as ações. OBS.: O formato da mensagem é tratado na classe Communication 
        this.communication.sendActionsToDevice(
                dev.getPreferredDeviceCommunication(), // Comunicação preferêncial, em breve trocar para coreografar entre todas as comunicações
                actions, // Ações
                user.getUsersEnvironment().getEnvironment(),
                exiting); 
        // retorna o estado OK da função;
        return "{\"status\":\"OK\"}";
    }
    
    public String validateRemoteLoginUser(String userName, String userPassword, String deviceCode){
        // Verifica login e senha do usuário, se sim retorna o status OK e continua senão retorna Status DENY 
        if (!this.userHasAccessPermission(userName, userPassword)) {
            return "{\"status\":\"DENY\"}"; // em breve fazer mensagens dinâmicas (Existem protocolos Diferentes)
        }
        // Futuramente é possível adicionar a funcionalidade de ver se o usuário tem permissão de usar o dispositivo
        return "{\"status\":\"OK\"}";
    }
    

    public String onChangeCurrentUserLocalizationReturnActions(int environmentId, String userName, String userPassword, String deviceCode,Boolean exiting) {
            // Verifica login e senha do usuário, se sim retorna o status OK e continua senão retorna Status DENY 
        if (!this.userHasAccessPermission(userName, userPassword)) {
            return "{\"status\":\"DENY\"}"; // em breve fazer mensagens dinâmicas (Existem protocolos Diferentes)
        }
        // Verifica se o dispositivo está cadastrado
        if(!this.devDAO.isDeviceRegistered(deviceCode)){
            return "{\"status\":\"ERROR\"}"; 
        }
        boolean hasChandedUserLocation = this.hasChangedUserEnvironment(userName, environmentId);
        boolean hasChangedDeviceLocation = this.hasChangedDeviceEnvironment(deviceCode, environmentId);
        // Caso nem o dispositivo nem o usuário mudaram de localização então não é necessário seguir em frente, retorna OK;
        if (!hasChandedUserLocation && !hasChangedDeviceLocation) {
            return "{\"status\":\"OK\"}";
        }
        // Verifica se o usuário continua no mesmo ambiente, se não atualiza a localização
        if (hasChandedUserLocation) {
            this.updateUserEnvironment(userName, environmentId); // em breve fazer mensagens dinâmicas (Existem protocolocos Diferentes)
        }
        // Verifica se o dispositivo continua no mesmo ambiente, se não atualiza localização
        if (hasChangedDeviceLocation) {
            this.updateDeviceEnvironment(deviceCode, environmentId);
        }
        // Busca as informações do Usuário no Ambiente e do Device
        Device dev = this.devDAO.get(deviceCode);
            // Busca as funcionalidades do Device
        dev.setListFunctionalities(devDAO.getDeviceFunctionalities(dev.getId()));
        // Busca Tipo de ambiente e o tipo de acesso do usuário no ambiente;
        User user = this.userDAO.getUserEnvironment(userName, environmentId);
        // Cria e salva Log de acesso (Contexto de Localização);
        LogEvent eve = new LogEvent();
        eve.setDevice(dev);
        eve.setTimeVariables(eve,new Date());
        eve.setShift(Character.MIN_VALUE); //
        eve.setWeekday(environmentId);
        eve.setWorkday(Character.MIN_VALUE);
        eve.setEnvironment(user.getUsersEnvironment().getEnvironment());
        eve.setEvent((exiting)? "Leaving the location":"Entering the location");
        eve.setUser(user);
        envDAO.insertLog(eve); /// Terminar de alterar
        // Função de Busca de ações
        // Busca Tipo de ambiente
        // Busca Busca o tipo de acesso do usuário no ambiente
        // Busca o nível de acesso do usuário e ações default
        // Busca se há ações customizadas
        // Sobrepoem as ações Default pelas customizadas
        ArrayList<Action> actions = this.getListActions(user, dev, eve);
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
        // Instancia uma lista de ações que conterá as regras a serem retornadas;
        ArrayList<Action> finalActions = new ArrayList<Action>(), customActions = new ArrayList<Action>();
        // Busca as informações sobre o dipositivo, bem como as funcionalidades que ele suporta
        Device dev = this.devDAO.get(deviceCode);
        // Busca Tipo de ambiente e o tipo de acesso do usuário no ambiente;
        User user = this.userDAO.getUserEnvironment(userName, environmentId);
        // Busca as regras (ações) padrão do nível de acesso resultante pelo tipo de tipo de ambiente e do tipo de acesso;
        AccessLevel accessLevel = this.accLevDAO.get(
                user.getCurrentEnvironment().getEnvironmentType(),
                user.getUsersEnvironment().getCurrentAccessType());
        // Busca se há ações customizadas
        customActions = this.actDAO.getCustomActions(environmentId, accessLevel.getId());
        // Sobrepoem as ações Default pelas customizadas
        // Seleciona na lista somente as ações referentes as funcionalidades que o dispositivo visado suporta, não considerando as demais. 
            // Inicia adicionando as ações padrão, caso exista alguma ação customizadas para o ambiente equivalente 
            // a alguma ação padrão então sobre escreve-á pela ação customizada equivalente;
        if (customActions.size() > 0) {
            for (i = 0; i < accessLevel.getActionsList().size(); i++) {
                for (j = 0; j < customActions.size(); j++) {
                    if (accessLevel.getActionsList().get(i).getId() == customActions.get(j).getId()) {
                        accessLevel.getActionsList().set(i, customActions.get(j));
                    }
                }
            }
        }

        // Seleciona na lista somente as ações referentes as funcionalidades que o dispositivo visado suporta, eliminando as demais;    
        for (i = 0; i < accessLevel.getActionsList().size(); i++) {
            for (j = 0; j < dev.getListFunctionalities().size(); j++) {
                if (dev.getListFunctionalities().get(j).getId() == accessLevel.getActionsList().get(i).getFunctionality().getId()) {
                    finalActions.add(accessLevel.getActionsList().get(i));
                }
            }
        }
        // Retorna a lista com as ações, se o dispositivo não possuía funcionalidades a lista retorna vazia
        return finalActions;
    }

    private ArrayList<Action> getListActions(User userEnvironment, Device device, LogEvent event) {
        int i, j;
        // Gera o classificador
        Classifier classifier = ClassifierFactory.create("NotImplemented"); // Não implementado
        // Classifica o tipo de acesso baseado nos 5 parâmetros (Profile,Tipo de Ambiente,Turno,Dia útil, Dia de semana)
        AccessType accessTypeClassified  = classifier.classify(
                userEnvironment.getUsersEnvironment().getUserProfile(), // Tipo de Profile: 1;"Unknown" 2;"Transient" 3;"User" 4;"Responsible" 5;"Student" 6;"Manager"
                userEnvironment.getUsersEnvironment().getEnvironment().getEnvironmentType(),  // Tipo de Ambiente: 1;"Blocked" 2;"Private" 3;"Public"
                event.getShift(),   // Turno:               LogEvent.DAY_SHIFT ou NIGHT_SHIFT
                event.getWorkday(), // Se é dia útil:       LogEvent.YES_WORKDAY ou NOT_WORKDAY
                event.getWeekday());// Se é dia de semana:  LogEvent.DAY_OF_WEEK; ou LogEvent.DAY_OF_WEEKEND;
        
        // Busca o nível de acesso do usuário e ações default
        AccessLevel accessLevel = this.accLevDAO.get( //
                userEnvironment.getUsersEnvironment().getEnvironment().getEnvironmentType(),
                accessTypeClassified); // old: userEnvironment.getUsersEnvironment().getCurrentAccessType()
        // Busca ações customizadas
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
    
    private String makeMessage(ArrayList<Action> actions) {
        return this.makeMessage(actions, "OK");
    }

    private String makeMessage(ArrayList<Action> actions, String status) {
        /* Inicia a mensagem adicionando o status e adicionando o nome do array */
        String json = "{\"status\":\"" + status + "\",\"actions\":[";
        /* Gera as ações no formato JSON baseado na lista  de ações passado por parâmetro */
        for (int i = 0; i < actions.size();i++) {
            json += "{\"fid\":" + actions.get(i).getFunctionality().getId() 
                    + ",\"action\":\"" + actions.get(i).getAction() + "\"}";
            if(i != (actions.size()-1)) json += ",";
        }
        
        /* pode ser feito usando JSON object */
        /* Instancia os objetos a serem utilizados;
        JSONObject message = new JSONObject(), temp;
        JSONArray jActions = new JSONArray();
        /* Gera as ações no formato JSON baseado na lista de ações passado por parâmetro;
        for (Action action : actions) {
            temp = new JSONObject();
            temp.put("fid", action.getFunctionality().getId());   
            temp.put("action", action.getAction());
            jActions.add(temp);
        }
        /* Adiciona o status e a lista na mensagem;
        message.put("status", status);
        message.put("actions", jActions);
        * Retorna a mensagem em formato JSON.
        return message.toJSONString(); */
        /* Retorna a mensagem em formato JSON. */
        return json + "]}";
    }
    
    private boolean isDeviceRegistered(String deviceCode) {
        return this.devDAO.isDeviceRegistered(deviceCode);
    }

    private boolean hasChangedDeviceEnvironment(String deviceCode, int environmentId) {
        return this.devDAO.hasChangedDeviceEnvironment(deviceCode, environmentId);
    }

    public String onInsertNewCommunicationCode(String userName, String userPassword, String deviceCode, String communicationCode, int communicationType, int communicationId,String deviceName,int[] functionalities) {
        Device device = null;
        // Verifica login e senha do usuário, se sim retorna o status OK e continua senão retorna Status DENY 
        if (!this.userHasAccessPermission(userName, userPassword)) {
            return "{\"status\":\"DENY\"}"; // em breve fazer mensagens dinâmicas (Existem protocolos Diferentes)
        }
        // Verifica se o dispositivo existe, se sim continua, se não adiciona na DB
        if(!this.devDAO.isDeviceRegistered(deviceCode)){
            device = new Device();
            device.setCode(deviceCode);
            device.setName((deviceName != null)?deviceName:("the device of "+userName));
            device.setUser(this.userDAO.getByUserName(userName, false) );
            device.setDeviceType(new DeviceType(1, "Android")); // Incompleto! fazer método dinâmico
            this.devDAO.insert(device);
             // Busca o dispositivo
            device = this.devDAO.get(deviceCode);
            // Insere as funcionalidades do dispositivo, caso possua
            if(functionalities != null)
                if(functionalities.length > 0)
                    this.devDAO.insertDeviceFunctionalities(functionalities, device);            
        }  else {
             // Busca o dispositivo
            device = this.devDAO.get(deviceCode);
        }       
       
        // insere a o novo código de cuminicação, se não houver comunicação cria uma nova;
        if(this.devDAO.onInsertCommunicationCode(
                device,
                communicationCode,
                communicationType,    // Google Cloud Message
                communicationId)){    // O primeiro que encontrar
            return "{\"status\":\"OK\"}";
        } else { return "{\"status\":\"ERROR\"}";}
    }
    
    public String getJsonEnvironments(String userName,String userPassword, String deviceCode,int version){
        /* Verifica se o usuário possui permissão */
        if (!this.userHasAccessPermission(userName, userPassword)) {
            return "{\"status\":\"DENY\"}";
        }  
        /* Verifica se o dispositivo está cadastrado, se não estiver retorna ERROR */
        if(!this.devDAO.isDeviceRegistered(deviceCode)){
            return "{\"status\":\"DENY\"}";
        }
        /* Retorna a última versão atualizada no banco de dados */
        int lastVersion = this.envDAO.getLastUpdatedVersion();
        /* Compara se a versão passada é a última, se sim retorna mensagem OK */
        if(version >= lastVersion){
            return "{\"status\":\"OK\",\"version\":\""+lastVersion+"\"}";
        }
        /* Busca ambientes iniciando pela atualização */
        ArrayList<Environment> list = this.envDAO.getListByVersion(version);
        /* Gera e retorna o JSON. Através da função makeJsonEnviromentMap */
        return this.makeJsonEnviromentMap(list,lastVersion);
    }
    
    public String makeJsonEnviromentMap(ArrayList<Environment> list,int version){
        /* Instancia os objetos que serão usados na função */
        JSONObject root = new JSONObject(), environmentJSON, point;
        JSONArray environments = new JSONArray(), pointsArrayJSON;
        /* Adiciona o status da mensagem e a última versão */
        root.put("status", "UPDATED");
        root.put("version", version);
        /* Converte as informações dos ambientes em JSON acicionando do JSONArray */
        for(Environment e : list){
            environmentJSON = new JSONObject();
            environmentJSON.put("id", e.getId());
            environmentJSON.put("version", e.getVersion());
            environmentJSON.put("name", e.getName());
            environmentJSON.put("locationType", e.getLocalizationType().getName());
            environmentJSON.put("latitude", e.getLatitude());
            environmentJSON.put("longitude", e.getLongitude());
            environmentJSON.put("altitude", e.getAltitude());
            environmentJSON.put("parentEnvironment", (e.getParentEnvironment() != null)?e.getParentEnvironment().getId():0);
            environmentJSON.put("operatingRange", e.getOperatingRange());
            pointsArrayJSON = new JSONArray();
            for(Point p : e.getEnvironmentPoints()){
                point = new JSONObject();
                point.put("latitude", p.getLatitude());
                point.put("longitude", p.getLongitude());
                point.put("altitude", p.getAltitude());
                pointsArrayJSON.add(point);
            }
            environmentJSON.put("map", pointsArrayJSON);
            environments.add(environmentJSON);
        }
        /* Troca adiciona na mensagem os ambientes convertidos */
        root.put("environments",environments);
        /* Retorna a mensagem */
        return root.toJSONString();
    }
   
}
