<%-- 
    Document   : removeusers
    Created on : 07/05/2014, 14:41:01
    Author     : Guazzelli
--%>

<%@page import="server.dao.DeviceCommunicationDAO"%>
<%@page import="server.model.DeviceCommunication"%>
<%@page import="server.util.AccessBD"%>


 <%
    AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
    DeviceCommunicationDAO devcomDAO = new DeviceCommunicationDAO(conexao);
    String[] ids = request.getParameterValues("check");            
    for (int i = 0; i < ids.length; i++) 
    {
        DeviceCommunication devcom = devcomDAO.get(Integer.parseInt(ids[i]),true);
        devcomDAO.delete(devcom);
    }
    response.sendRedirect("list.jsp");
 %>