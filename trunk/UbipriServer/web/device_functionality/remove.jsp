<%-- 
    Document   : removeusers
    Created on : 07/05/2014, 14:41:01
    Author     : Guazzelli
--%>

<%@page import="server.dao.DeviceFunctionalityDAO"%>
<%@page import="server.model.DeviceFunctionality"%>
<%@page import="server.util.AccessBD"%>


 <%
    AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
    DeviceFunctionalityDAO funcDAO = new DeviceFunctionalityDAO(conexao);
    String[] ids = request.getParameterValues("check");
    for (int i = 0; i < ids.length; i++) 
    {
        String[] temp = ids[i].split(",");
        DeviceFunctionality func = new DeviceFunctionality(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]));
        funcDAO.delete(func);
    }
    response.sendRedirect("list.jsp");
 %>