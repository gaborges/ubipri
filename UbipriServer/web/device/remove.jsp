<%-- 
    Document   : removeusers
    Created on : 07/05/2014, 14:41:01
    Author     : Guazzelli
--%>

<%@page import="server.dao.DeviceDAO"%>
<%@page import="server.model.Device"%>
<%@page import="server.util.AccessBD"%>


 <%
    AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
    DeviceDAO deviceDAO = new DeviceDAO(conexao);
    String[] ids = request.getParameterValues("check");            
    for (int i = 0; i < ids.length; i++) 
    {
        Device device = deviceDAO.get(Integer.parseInt(ids[i]),true);
        deviceDAO.delete(device);
    }
    response.sendRedirect("list.jsp");
 %>