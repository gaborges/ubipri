<%-- 
    Document   : removeusers
    Created on : 07/05/2014, 14:41:01
    Author     : Guazzelli
--%>

<%@page import="server.dao.LocalizationTypeDAO"%>
<%@page import="server.model.LocalizationType"%>
<%@page import="server.util.AccessBD"%>


 <%
    AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
    LocalizationTypeDAO locDAO = new LocalizationTypeDAO(conexao);
    String[] ids = request.getParameterValues("check");            
    for (int i = 0; i < ids.length; i++) 
    {
        LocalizationType loc = locDAO.get(Integer.parseInt(ids[i]),true);
        locDAO.delete(loc);
    }
    response.sendRedirect("list.jsp");
 %>