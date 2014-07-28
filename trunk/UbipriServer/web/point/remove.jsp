<%-- 
    Document   : removeusers
    Created on : 07/05/2014, 14:41:01
    Author     : Guazzelli
--%>

<%@page import="server.dao.PointDAO"%>
<%@page import="server.model.Point"%>
<%@page import="server.util.AccessBD"%>


 <%
    AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
    PointDAO pointDAO = new PointDAO(conexao);
    String[] ids = request.getParameterValues("check");            
    for (int i = 0; i < ids.length; i++) 
    {
        Point point = pointDAO.get(Integer.parseInt(ids[i]),true);
        pointDAO.delete(point);
    }
    response.sendRedirect("list.jsp");
 %>