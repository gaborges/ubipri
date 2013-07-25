/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.modules.communication.rest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import server.modules.communication.Communication;
import server.modules.communication.InsertCommunicationCodeParameters;
import server.modules.communication.Parameters;
import server.modules.communication.RemoteLoginParameters;
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
     * Retrieves representation of an instance of server.modules.communication.rest.GlobalCommunicationResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        return "{hello:world}";
    }
    
    /**
     * 1) Cadastro do código de comunicação por Google Cloud Message.
     * Path: http://host:port/UbipriServer/webresources/rest/insert/communicationCode/gcm
     * @param String userName, String userPassword, String deviceCode, newCommunicationCode:String .
     * Message Format: {"userName":"user_name", "userPassword":"12345", "deviceCode":"Ae123sadSfas4fa", "communicationCode":"AABBCC321"}
     * @return Status of the operation in the Server.
     * Return Message Format: {"status":"OK"},{"status":"ERROR"} or{"status":"DENNY"}
     */
    
    @POST
    @Path("/insert/communicationCode/gcm")
    @Consumes("application/json")
    @Produces("application/json") 
    public String insertGoogleCloudMessageCommunicationCode(InsertCommunicationCodeParameters p) {
        Communication comm = new Communication(new PrivacyControlUbiquitous());
        return comm.onInsertNewCommunicationCode(
                p.getUserName(),
                p.getUserPassword(), 
                p.getDeviceCode(),
                p.getCommunicationCode(),
                4,      // Google Cloud Message
                -1);    // O primeiro que encontrar
    }
    
    /**
     * 2) Cadastro do código de comunicação para Comunicações Genéricas. (Não implementado)
     * Path: http://host:port/UbipriServer/webresources/rest/insert/communicationCode/generic
     * @param String userName, String userPassword, String deviceCode, newCommunicationCode:String, communicationType:int, communicationId :int.
     * Message Format: {"userName":"user_name", "userPassword":"12345", "deviceCode":"Ae123sadSfas4fa", "communicationCode":"AABBCC321"}
     * @return Status of the operation in the Server.
     * Return Message Format: {"status":"OK"},{"status":"ERROR"} or{"status":"DENNY"}
     */
    
    
    /**
     * 3) Envio de atualização de localização, sem resposta de ações
     * Path: http://host:port/UbipriServer/webresources/rest/change/location/json
     * @param int environmentId, String userName, String userPassword, String deviceCode.
     * Message Format: {"userCode":"user_name", "userPassword":"12345", "deviceCode":"Ae123sadSfas4fa", "environmentId":1}
     * @return Status of the operation in the Server.
     * Return Message Format: {"status":"OK"},{"status":"ERROR"} or{"status":"DENNY"}
     */
    
    @PUT
    @Path("/change/location/json")
    @Consumes("application/json")
    @Produces("application/json") 
    public String onChangeCurrentUserLocalization(Parameters p) {
        Communication comm = new Communication(new PrivacyControlUbiquitous());
        return comm.onChangeCurrentUserLocalization(
                p.getEnvironmentId(), p.getUserName(),p.getUserPassword(), p.getDeviceCode());
    }
    
    /**
     * 4) Envio de atualização de localização, com resposta de ações
     * Path: http://host:port/UbipriServer/webresources/rest/change/location/json/response
     * @param int environmentId, String userName, String userPassword, String deviceCode.
     * Message Format: {"userName":"user_name", "userPassword":"12345", "deviceCode":"Ae123sadSfas4fa", "environmentId":1}
     * @return Status of the operation in the Server ("status" has three possible values: OK, ERROR or DENNY).
     * The seconde parameter contais the actions to be returned.
     * Message Format Return: {"status":"OK","actions":[{"fid":"1","action":"on"},{"fid":"3","action":"off"}]}
     */    
    @PUT
    @Path("/change/location/json/response")
    @Consumes("application/json")
    @Produces("application/json")
    public String onChangeCurrentUserLocalizationWithResponse(Parameters p) {
        Communication comm = new Communication(new PrivacyControlUbiquitous());
        //System.out.println("{"+p.getDeviceCode()+","+p.getUserName()+","+p.getEnvironmentId()+"}");
        return comm.onChangeCurrentUserLocalizationWithResponse(
                p.getEnvironmentId(), p.getUserName(),p.getUserPassword(), p.getDeviceCode());
    }
    
    /**
     * 5) Valida login do usuário
     * Path: http://host:port/UbipriServer/webresources/rest/validate/{login}/{password}/{device}
     * @param String userName, String userPassword, String deviceName.
     * Message Format: http://host:port/UbipriServer/webresources/rest/validate/{user_name}/{login}/{deviceCode}
     * @return Status of the operation in the Server.
     * Return Message Format: {"status":"OK"},{"status":"ERROR"} or{"status":"DENNY"}
     */
    @GET
    @Path("/validate/{login}/{password}/{device}")
    @Produces("application/json")
    public String validateRemoteLoginUser(
            @PathParam("login")String userName,
            @PathParam("password")String userPassword,
            @PathParam("device")String deviceCode) {
        Communication comm = new Communication(new PrivacyControlUbiquitous());
        return comm.validateRemoteLoginUser(userName, userPassword, deviceCode);
    }
    
    /**
     * 6) Buscar Ambientes
     * Path: http://host:port/UbipriServer/webresources/rest/get/environment/map
     * Consumes: application/json
     * Produces: application/xml
     * Method: GET
     * @param Params: userName : String, userPassword : String, deviceCode : String, [latitude : double], [longitude : double],[simbolicBaseEnvironment : String].
     * Message Format: Message format: { "userName":"bruno", "userPassword":"12345", "deviceCode":"4444444444"", "latitude ":"-30.00002313", "longitude ":"-51.76672364","simbolicBaseEnvironment ":"Porto Alegre"}
     * @return Mapped environments with XML.
     * Return Message Format: <>xml com elementos - pegar do eclipse</>
     */
    
    /**
     * 7) Envio de atualização de localização, por provedor de localização, sem resposta de ações (GUILHERME)
     * Descrição: Método utilizado para atualizar a localização de um dispositivo e de um usuário através de um provedor, RFID com arduino pode ser um exemplo.
     * Path: http://host:port/UbipriServer/webresources/rest/change/location/json/provider
     * @param environmentId : int , userName : String, userPassword : String, deviceCode : String, providerCode : String.
     * Message Format: {"userCode":"user_name", "userPassword":"12345", "deviceCode":"Ae123sadSfas4fa", "environmentId":1,"providerCode ":"876541112"}
     * @return Status of the operation in the Server.
     * Return Message Format: {"status":"OK"},{"status":"ERROR"} or{"status":"DENNY"}
     */
    
    
    
}
