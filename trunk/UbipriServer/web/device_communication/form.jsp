<%-- 
    Document   : newuser
    Created on : 01/04/2014, 17:06:57
    Author     : Guazzelli
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="server.dao.CommunicationTypeDAO"%>
<%@page import="server.dao.DeviceCommunicationDAO"%>
<%@page import="server.model.DeviceCommunication"%>
<%@page import="server.model.CommunicationType"%>
<%@page import="server.model.User"%>
<%@page import="server.util.AccessBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" media="screen" href="../style.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New DeviceCommunication</title>
    </head>
    <script type="text/javascript" SRC="../Scripts.js" ></script>


    <body>

        <%
            AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
            DeviceCommunicationDAO devcomDAO = new DeviceCommunicationDAO(conexao);            

            if (request.getMethod().equals("POST")) {
                DeviceCommunication devcom = new DeviceCommunication();// Seta o ID
                devcom.setName(request.getParameter("name"));
                devcom.setAddress(request.getParameter("address"));
                devcom.setParameters(request.getParameter("parameters"));
                devcom.setAddressFormat(request.getParameter("address_format"));
                devcom.setPort(request.getParameter("port"));                
                /*devcom.setDevice(
                 new Device(localrs.getInt(""), localrs.getString(""),localrs.getDouble(""), localrs.getString("")));*/
                devcom.setCommunicationType(new CommunicationType(Integer.parseInt(request.getParameter("communication_type")),"null"));
                devcom.setPreferredOrder(Integer.parseInt(request.getParameter("preferred_order")));
                

                devcomDAO.insert(devcom);
                response.sendRedirect("list.jsp");
            }
        %>    
        <div class="form">
            <div>
                <h2>New Device Communication</h2>
            </div>
            <div>
                <table align="center">
                    <tbody>
                    <form name="submit" method="POST" onSubmit="return validaform();"> 
                        <tr>
                            <th><label>Name:</label></th>
                            <td><input type="text" name="name"/></td>
                        </tr>
                        <tr>
                            <th><label>Address:</label></th>
                            <td><input type="text" name="address"/></td>
                        </tr>
                        <tr>
                            <th><label>Parameters:</label></th>
                            <td><input type="text" name="parameters"/></td>
                        </tr>
                        <tr>
                            <th><label>Address Format:</label></th>
                            <td><input type="text" name="address_format"/></td>
                        </tr>
                        <tr>
                            <th><label>Port:</label></th>
                            <td><input type="text" name="port"/></td>
                        </tr>
                        <tr>
                            <th><label>Communication Type:</label></th>
                            <td><select name="loctype">
                                    <%
                                        CommunicationTypeDAO commtypeDAO = new CommunicationTypeDAO(conexao);
                                        ArrayList<CommunicationType> commtypes = commtypeDAO.getList();

                                        CommunicationType commtype;

                                        for (int i = 0; i < commtypes.size(); i++) {
                                                commtype = commtypes.get(i);%>
                                    <option value="<%=commtype.getId()%>"><%=commtype.getName()%></option>
                                    <%}%>
                                </select></td>
                        </tr>
                        <tr>
                            <th><label>Preferred Order:</label></th>
                            <td><input type="text" name="preferred_order"/></td>
                        </tr>
                        <tr>
                            <td style="text-align: right;"><input type="submit" value="Include"/></td>
                    </form>
                    <form action="list.jsp">
                        <td><input type="submit" value="Cancel" name="cancelar"/></td>
                        </tr>   
                    </form>
                        </tbody>
                </table>
            </div>
                <div id="mensagem"></div>
        </div>
    </body>
</html>
