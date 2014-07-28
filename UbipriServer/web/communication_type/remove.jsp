<%-- 
    Document   : removeusers
    Created on : 07/05/2014, 14:41:01
    Author     : Guazzelli
--%>

<%@page import="server.dao.CommunicationTypeDAO"%>
<%@page import="server.model.CommunicationType"%>
<%@page import="server.util.AccessBD"%>


 <%
    AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
    CommunicationTypeDAO commDAO = new CommunicationTypeDAO(conexao);
    String[] ids = request.getParameterValues("check");            
    for (int i = 0; i < ids.length; i++) 
    {
        CommunicationType comm = commDAO.get(Integer.parseInt(ids[i]),true);
        commDAO.delete(comm);
    }
    response.sendRedirect("list.jsp");
 %>