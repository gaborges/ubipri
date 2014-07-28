<%-- 
    Document   : removeusers
    Created on : 07/05/2014, 14:41:01
    Author     : Guazzelli
--%>

<%@page import="server.dao.FunctionalityDAO"%>
<%@page import="server.model.Functionality"%>
<%@page import="server.util.AccessBD"%>


 <%
    AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
    FunctionalityDAO funcDAO = new FunctionalityDAO(conexao);
    String[] ids = request.getParameterValues("check");            
    for (int i = 0; i < ids.length; i++) 
    {
        Functionality func = funcDAO.get(Integer.parseInt(ids[i]),true);
        funcDAO.delete(func);
    }
    response.sendRedirect("list.jsp");
 %>