<%-- 
    Document   : removeusers
    Created on : 07/05/2014, 14:41:01
    Author     : Guazzelli
--%>

<%@page import="server.dao.UserDAO"%>
<%@page import="server.model.User"%>
<%@page import="server.util.AccessBD"%>


 <%
    AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
    UserDAO useDAO = new UserDAO(conexao);
    String[] ids = request.getParameterValues("check");            
    for (int i = 0; i < ids.length; i++) 
    {
        User user = useDAO.get(Integer.parseInt(ids[i]),true);
        useDAO.delete(user);
    }
    response.sendRedirect("list.jsp");
 %>