<%-- 
    Document   : updateuser
    Created on : 24/04/2014, 15:22:43
    Author     : Guazzelli
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="server.dao.CommunicationTypeDAO"%>
<%@page import="server.model.CommunicationType"%>
<%@page import="server.dao.DeviceCommunicationDAO"%>
<%@page import="server.model.DeviceCommunication"%>
<%@page import="server.util.AccessBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" media="screen" href="../style.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Device Communication</title>
    </head>
    <script type="text/javascript" SRC="../Scripts.js" ></script>



    <body>

        <div  class="form">
            <div>
                <h2>Update Device Communication</h2>
            </div>
            <div>
                <table>
                    <tbody>
                        <%
                            AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
                            DeviceCommunicationDAO devcomDAO = new DeviceCommunicationDAO(conexao);

                            DeviceCommunication device1 = devcomDAO.get(Integer.parseInt(request.getParameter("id")), true);

                            if (request.getMethod().equals("POST")) {
                                
                                DeviceCommunication devcom = new DeviceCommunication();// Seta o ID
                                devcom.setId(Integer.parseInt(request.getParameter("id")));
                                devcom.setName(request.getParameter("name"));
                                devcom.setAddress(request.getParameter("address"));
                                devcom.setParameters(request.getParameter("parameters"));
                                devcom.setAddressFormat(request.getParameter("address_format"));
                                devcom.setPort(request.getParameter("port"));
                                /*devcom.setDevice(
                                 new Device(localrs.getInt(""), localrs.getString(""),localrs.getDouble(""), localrs.getString("")));*/
                                devcom.setCommunicationType(new CommunicationType(Integer.parseInt(request.getParameter("communication_type")), "null"));
                                devcom.setPreferredOrder(Integer.parseInt(request.getParameter("preferred_order")));

                                devcomDAO.update(devcom);
                                //response.sendRedirect("list.jsp");
                            }

                        %>
                    <form name="submit" method="POST" onSubmit="return validaform();"> 
                        <tr>
                            <th><label>ID:</label></th>
                            <td><input type="hidden" name="id" value="<%=device1.getId()%>"><b><%=device1.getId()%></b></td>
                        </tr>
                        <tr>
                            <th><label>Name:</label></th>
                            <td><input type="text" name="name" value="<%=device1.getName()%>"/></td>
                        </tr>
                        <tr>
                            <th><label>Address:</label></th>
                            <td><input type="text" name="address" value="<%=device1.getAddress()%>"/></td>
                        </tr>
                        <tr>
                            <th><label>Parameters:</label></th>
                            <td><input type="text" name="parameters" value="<%=device1.getParameters()%>"/></td>
                        </tr>
                        <tr>
                            <th><label>Address Format:</label></th>
                            <td><input type="text" name="address_format" value="<%=device1.getAddressFormat()%>"/></td>
                        </tr>
                        <tr>
                            <th><label>Port:</label></th>
                            <td><input type="text" name="port" value="<%=device1.getPort()%>"/></td>
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
                                    <option value="<%=commtype.getId()%>"<%if (device1.getCommunicationType().getId() == commtype.getId()) {%>selected<%}%>><%=commtype.getName()%></option>
                                    <%}%>
                                </select></td>
                        </tr>
                        <tr>
                            <th><label>Preferred Order:</label></th>
                            <td><input type="text" name="preferred_order" value="<%=device1.getPreferredOrder()%>"/></td>
                        </tr>
                        <tr>
                        <td class="accept"><input type="submit" value="Atualizar"/></td>
                    </form>
                    <form action="list.jsp">
                        <td><input type="submit" value="Cancelar" name="cancelar" /></td>
                        </tr>   
                    </form>
                    </tbody>
                </table>
            </div>
            <div id="mensagem"></div>
        </div>
    </body>
</html>