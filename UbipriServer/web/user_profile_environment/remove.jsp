<%-- 
    Document   : removeusers
    Created on : 07/05/2014, 14:41:01
    Author     : Guazzelli
--%>

<%@page import="server.dao.UserProfileEnvironmentDAO"%>
<%@page import="server.model.UserProfileEnvironment"%>
<%@page import="server.util.AccessBD"%>


 <%
    AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
    UserProfileEnvironmentDAO useproenvDAO = new UserProfileEnvironmentDAO(conexao);
    String[] ids = request.getParameterValues("check");            
    for (int i = 0; i < ids.length; i++) 
    {
        UserProfileEnvironment useproenv = useproenvDAO.get(Integer.parseInt(ids[i]),true);
        useproenvDAO.delete(useproenv);
    }
    response.sendRedirect("list.jsp");
 %>