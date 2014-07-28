<%-- 
    Document   : removeusers
    Created on : 07/05/2014, 14:41:01
    Author     : Guazzelli
--%>

<%@page import="server.dao.EnvironmentDAO"%>
<%@page import="server.model.Environment"%>
<%@page import="server.util.AccessBD"%>


 <%
    AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
    EnvironmentDAO envDAO = new EnvironmentDAO(conexao);
    String[] ids = request.getParameterValues("check");            
    for (int i = 0; i < ids.length; i++) 
    {
        Environment env = envDAO.get(Integer.parseInt(ids[i]),true);
        envDAO.delete(env);
    }
    response.sendRedirect("list.jsp");
 %>