<%-- 
    Document   : removeusers
    Created on : 07/05/2014, 14:41:01
    Author     : Guazzelli
--%>

<%@page import="server.dao.LogEventDAO"%>
<%@page import="server.model.LogEvent"%>
<%@page import="server.util.AccessBD"%>


 <%
    AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
    LogEventDAO logDAO = new LogEventDAO(conexao);
    String[] ids = request.getParameterValues("check");            
    for (int i = 0; i < ids.length; i++) 
    {
        LogEvent log = logDAO.get(Integer.parseInt(ids[i]),true);
        logDAO.delete(log);
    }
    response.sendRedirect("list.jsp");
 %>