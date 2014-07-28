<%-- 
    Document   : removeusers
    Created on : 07/05/2014, 14:41:01
    Author     : Guazzelli
--%>

<%@page import="server.dao.DeviceTypeDAO"%>
<%@page import="server.model.DeviceType"%>
<%@page import="server.util.AccessBD"%>


 <%
    AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
    DeviceTypeDAO devtypeDAO = new DeviceTypeDAO(conexao);
    String[] ids = request.getParameterValues("check");            
    for (int i = 0; i < ids.length; i++) 
    {
        DeviceType devtype = devtypeDAO.get(Integer.parseInt(ids[i]),true);
        devtypeDAO.delete(devtype);
    }
    response.sendRedirect("list.jsp");
 %>