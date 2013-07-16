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
import javax.ws.rs.Produces;
import server.modules.communication.Communication;
import server.modules.communication.Parameters;

/**
 * REST Web Service
 *
 * @author guilherme
 */
@Path("globalCommunication")
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
     * PUT method for updating or creating an instance of GlobalCommunicationResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Path("/change/localization/json")
    @Consumes("application/json")
    @Produces("text/plain") 
    public String onChangeCurrentUserLocalization(Parameters p) {
        Communication comm = new Communication();
        return comm.onChangeCurrentUserLocalization(
                p.getEnvironmentId(), p.getUserName(),p.getUserPassword(), p.getDeviceCode());
    }
    
    @PUT
    @Path("/change/localization/json/response")
    @Consumes("application/json")
    @Produces("application/json")
    public String onChangeCurrentUserLocalizationWithResponse(Parameters p) {
        Communication comm = new Communication();
        //System.out.println("{"+p.getDeviceCode()+","+p.getUserName()+","+p.getEnvironmentId()+"}");
        return comm.onChangeCurrentUserLocalizationWithResponse(
                p.getEnvironmentId(), p.getUserName(),p.getUserPassword(), p.getDeviceCode());
    }
    
    
}
