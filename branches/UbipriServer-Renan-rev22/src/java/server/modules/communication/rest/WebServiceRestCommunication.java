/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.modules.communication.rest;

import com.sun.jersey.core.util.Base64;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import server.modules.communication.Communication;
import server.modules.communication.GetEnvironmentParameters;
import server.modules.communication.InsertCommunicationCodeParameters;
import server.modules.communication.Parameters;
import server.modules.privacy.PrivacyControlUbiquitous;


/**
 * REST Web Service
 *
 * @author guilherme
 */
@Path("rest")
public class WebServiceRestCommunication {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GlobalCommunicationResource
     */
    public WebServiceRestCommunication() {
    }

    /**
     * Retrieves representation of an instance of
     * server.modules.communication.rest.GlobalCommunicationResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        return "{hello:world}";
    }

    /**
     * 1) Cadastro do código de comunicação por Google Cloud Message. Path:
     * http://host:port/UbipriServer/webresources/rest/insert/communicationCode/gcm
     *
     * @param String userName, String userPassword, String deviceCode,
     * newCommunicationCode:String, [deviceName: String]. Message Format:
     * {"userName":"user_name", "userPassword":"12345",
     * "deviceCode":"Ae123sdevcom_addressadSfas4fa", "communicationCode":"AABBCC321",
     * "deviceName":"Smartphone do Bruno"}
     * @return Status of the operation in the Server. Return Message Format:
     * {"status":"OK"},{"status":"ERROR"} or{"status":"DENY"}
     */
    @POST
    @Path("/insert/communicationCode/gcm")
    @Consumes("application/json")
    @Produces("application/json")
    public String insertGoogleCloudMessageCommunicationCode(InsertCommunicationCodeParameters p,
            @HeaderParam("Authentication") String auth) {
        // Cheva se a autenticação está vindo por parâmetro
        if(auth != null){
            String a[] = auth.split(" ");
            String o = new String(Base64.decode(a[1]));
            //System.out.println("base64: "+auth+" -  desconvertido: "+o);
            a = o.split(":");
            if(a.length > 1){
                p.setUserName(a[0]);     // Login
                p.setUserPassword(a[1]); // Password
            }
        }
        Communication comm = new Communication(new PrivacyControlUbiquitous());
        //System.out.println(p.toString());
        return comm.onInsertNewCommunicationCode(
                p.getUserName(),
                p.getUserPassword(),
                p.getDeviceCode(),
                p.getCommunicationCode(),
                4, // Google Cloud Message
                -1, // O primeiro que encontrar
                p.getDeviceName(),
                p.getFunctionalities());
    }

    /**
     * 2) Cadastro do código de comunicação para Comunicações Genéricas. (Não
     * implementado) Path:
     * http://host:port/UbipriServer/webresources/rest/insert/communicationCode/generic
     *
     * @param String userName, String userPassword, String deviceCode,
     * newCommunicationCode:String, communicationType:int, communicationId :int,
     * [deviceName: String]. Message Format: {"userName":"user_name",
     * "userPassword":"12345", "deviceCode":"Ae123sadSfas4fa",
     * "communicationCode":"AABBCC321", "deviceName":"Smartphone do Bruno"}
     * @return Status of the operation in the Server. Return Message Format:
     * {"status":"OK"},{"status":"ERROR"} or{"status":"DENNY"}
     */
    /**
     * 3) Envio de atualização de localização, sem resposta de ações Path:
     * http://host:port/UbipriServer/webresources/rest/change/location/json
     *
     * @param int environmentId, String userName, String userPassword, String
     * deviceCode. Message Format: {"userCode":"user_name",
     * "userPassword":"12345", "deviceCode":"Ae123sadSfas4fa",
     * "environmentId":1}
     * @return Status of the operation in the Server. Return Message Format:
     * {"status":"OK"},{"status":"ERROR"} or{"status":"DENNY"}
     */
    @PUT
    @Path("/change/location/json")
    @Consumes("application/json")
    @Produces("application/json")
    public String onChangeCurrentUserLocalization(Parameters p,
            @HeaderParam("Authentication") String auth) {
        // Cheva se a autenticação está vindo por parâmetro
        if(auth != null){
            String a[] = auth.split(" ");
            String o = new String(Base64.decode(a[1]));
            //System.out.println("base64: "+auth+" -  desconvertido: "+o);
            a = o.split(":");
            if(a.length > 1){
                p.setUserName(a[0]);     // Login
                p.setUserPassword(a[1]); // Password
            }
        }
        Communication comm = new Communication(new PrivacyControlUbiquitous());
        return comm.onChangeCurrentUserLocalization(
                p.getEnvironmentId(), p.getUserName(), p.getUserPassword(), p.getDeviceCode(),
                    // Por padrão exiting é false, se vier nulo quer dizer que não foi passado por parâmetro
                (p.getExiting() == null)? false : 
                    // se retornar true será true senão qualquer outra forma será false
                ((p.getExiting().equals("true"))? true : false));
    }

    /**
     * 4) Envio de atualização de localização, com resposta de ações Path:
     * http://host:port/UbipriServer/webresources/rest/change/location/json/response
     *
     * @param int environmentId, String userName, String userPassword, String
     * deviceCode. Message Format: {"userName":"user_name",
     * "userPassword":"12345", "deviceCode":"Ae123sadSfas4fa",
     * "environmentId":1}
     * @return Status of the operation in the Server ("status" has three
     * possible values: OK, ERROR or DENNY). The seconde parameter contais the
     * actions to be returned. Message Format Return:
     * {"status":"OK","actions":[{"fid":"1","action":"on"},{"fid":"3","action":"off"}]}
     */
    @PUT
    @Path("/change/location/json/response")
    @Consumes("application/json")
    @Produces("application/json")
    public String onChangeCurrentUserLocalizationWithResponse(Parameters p,
            @HeaderParam("Authentication") String auth) {
        // Cheva se a autenticação está vindo por parâmetro
        if(auth != null){
            String a[] = auth.split(" ");
            String o = new String(Base64.decode(a[1]));
            //System.out.println("base64: "+auth+" -  desconvertido: "+o);
            a = o.split(":");
            if(a.length > 1){
                p.setUserName(a[0]);     // Login
                p.setUserPassword(a[1]); // Password
            }
        }
        Communication comm = new Communication(new PrivacyControlUbiquitous());
        //System.out.println("{"+p.getDeviceCode()+","+p.getUserName()+","+p.getEnvironmentId()+"}");
        return comm.onChangeCurrentUserLocalizationWithResponse(
                p.getEnvironmentId(), p.getUserName(), p.getUserPassword(), p.getDeviceCode(),
                    // Por padrão exiting é false, se vier nulo quer dizer que não foi passado por parâmetro
                (p.getExiting() == null)? false : 
                    // se retornar true será true senão qualquer outra forma será false
                ((p.getExiting().equals("true"))? true : false));
    }

    /**
     * 5) Valida login do usuário Path:
     * http://host:port/UbipriServer/webresources/rest/validate/{login}/{password}/{device}
     *
     * @param String userName, String userPassword, String deviceName. Message
     * Format:
     * http://host:port/UbipriServer/webresources/rest/validate/{user_name}/{login}/{deviceCode}
     * @return Status of the operation in the Server. Return Message Format:
     * {"status":"OK"},{"status":"ERROR"} or{"status":"DENNY"}
     */
    @GET
    @Path("/validate/{login}/{password}/{device}")
    @Produces("application/json")
    public String validateRemoteLoginUser(
            @PathParam("login") String userName,
            @PathParam("password") String userPassword,
            @PathParam("device") String deviceCode,
            @HeaderParam("Authentication") String auth) {
        if(auth != null){
            String a[] = auth.split(" ");
            String o = new String(Base64.decode(a[1]));
            //System.out.println("base64: "+auth+" -  desconvertido: "+o);
            a = o.split(":");
            if(a.length > 1){
                userName = a[0];     // Login
                userPassword = a[1]; // Password
            }
        }
        Communication comm = new Communication(new PrivacyControlUbiquitous());
        return comm.validateRemoteLoginUser(userName, userPassword, deviceCode);
    }
    /**
     * 6) Buscar Ambientes em XML Path:
     * http://host:port/UbipriServer/webresources/rest/get/environment/map
     * Consumes: application/json Produces: application/xml Method: GET
     *
     * @param Params: userName : String, userPassword : String, deviceCode :
     * String, [latitude : double], [longitude :
     * double],[simbolicBaseEnvironment : String]. Message Format: Message
     * format: { "userName":"bruno", "userPassword":"12345",
     * "deviceCode":"4444444444"", "latitude ":"-30.00002313", "longitude
     * ":"-51.76672364","simbolicBaseEnvironment ":"Porto Alegre"}
     * @return Mapped environments with XML. Return Message Format: <>xml com
     * elementos - pegar do eclipse</>
     */
    /**
     * 7) Buscar Ambientes em JSON Path:
     * http://host:port/UbipriServer/webresources/rest/get/environment/map/json
     * Consumes: application/json Produces: application/xml Method: GET
     *
     * @param Params: userName : String, userPassword : String, deviceCode :
     * String, currentVersion : int, [latitude : double], [longitude :
     * double],[simbolicBaseEnvironment : String]. Message Format: Message
     * format: { "userName":"bruno", "userPassword":"12345","currentVersion" :
     * "0", "deviceCode":"4444444444"", "latitude ":"-30.00002313", "longitude
     * ":"-51.76672364","simbolicBaseEnvironment ":"Porto Alegre"}
     * @return Mapped environments with JSON. Return Message Format: {
     * "status":"UPDATED", // OK (Não foi atualizado), ERROR e UPDATED (Há
     * Atualizações) "version":"1", // Versão atual das atualizações dos
     * ambientes no servidor "environments": // Ambientes retornados [ {
     * "id":"1", "version":"1", // Versão que foi incluída ou modificada
     * "name":"Porto Alegre", "latitude":"-30.072296142578118",
     * "longitude":"-51.17763595581054", "altitude":"0.0", // em metros
     * "parentEnvironment":"0", // Valor 0 não possui pai, é raiz
     * "operatingRange":"17550.786", // em metros "map":[ // Caso o ambiente
     * possuam pontos que o delimitem, em ordem, // formando um circuito
     * fechado, onde o último aponta para o primeiro
     * {"latitude":"-29.9612808227539","longitude":"-51.198184967041",
     * "altitude":"0.0"},
     * {"latitude":"-30.1073989868164","longitude":"-51.2952117919922",
     * "altitude":"0.0"},
     * {"latitude":"-30.2264022827148","longitude":"-51.216136932373",
     * "altitude":"0.0"},
     * {"latitude":"-30.0949935913086","longitude":"-51.0650444030762",
     * "altitude":"0.0"},
     * {"latitude":"-29.9714050292969","longitude":"-51.1136016845703",
     * "altitude":"0.0"}
     *
     * ]
     * }
     * ]
     * }
     */
    
    @POST
    @Path("/get/environment/map/json")
    @Consumes("application/json")
    @Produces("application/json")
    public String getAmbientesJson(GetEnvironmentParameters p,
            @HeaderParam("Authentication") String auth) {
        // Cheva se a autenticação está vindo por parâmetro
        if(auth != null){
            String a[] = auth.split(" ");
            String o = new String(Base64.decode(a[1]));
            //System.out.println("base64: "+auth+" -  desconvertido: "+o);
            a = o.split(":");
            if(a.length > 1){
                p.setUserName(a[0]);     // Login
                p.setUserPassword(a[1]); // Password
            }
        }
      
        Communication comm = new Communication(new PrivacyControlUbiquitous());
        System.out.println("res: "+p.toString());
        
        return comm.getEnvironmentJSONMap(
                p.getUserName(), 
                p.getUserPassword(), 
                p.getDeviceCode(), 
                p.getCurrentVersion());
    }
    
    /**
     * 8) Envio de atualização de localização, por provedor de localização, sem
     * resposta de ações (GUILHERME) Descrição: Método utilizado para atualizar
     * a localização de um dispositivo e de um usuário através de um provedor,
     * RFID com arduino pode ser um exemplo. Path:
     * http://host:port/UbipriServer/webresources/rest/change/location/json/provider
     *
     * @param environmentId : int , userName : String, userPassword : String,
     * deviceCode : String, providerCode : String. Message Format:
     * {"userCode":"user_name", "userPassword":"12345",
     * "deviceCode":"Ae123sadSfas4fa", "environmentId":1,"providerCode
     * ":"876541112"}
     * @return Status of the operation in the Server. Return Message Format:
     * {"status":"OK"},{"status":"ERROR"} or{"status":"DENNY"}
     */
}
