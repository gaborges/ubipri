<%-- 
    Document   : removeusers
    Created on : 07/05/2014, 14:41:01
    Author     : Guazzelli
--%>

<%@page import="server.dao.EnvironmentTypeDAO"%>
<%@page import="server.model.EnvironmentType"%>
<%@page import="server.util.AccessBD"%>


 <%
    AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
    EnvironmentTypeDAO envDAO = new EnvironmentTypeDAO(conexao);
    String[] ids = request.getParameterValues("check");            
    for (int i = 0; i < ids.length; i++) 
    {
        EnvironmentType env = envDAO.get(Integer.parseInt(ids[i]),true);
        envDAO.delete(env);
    }
    response.sendRedirect("list.jsp");
 %>