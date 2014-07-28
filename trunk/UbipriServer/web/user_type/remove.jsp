<%-- 
    Document   : removeusers
    Created on : 07/05/2014, 14:41:01
    Author     : Guazzelli
--%>

<%@page import="server.dao.UserTypeDAO"%>
<%@page import="server.model.UserType"%>
<%@page import="server.util.AccessBD"%>


 <%
    AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
    UserTypeDAO usertypeDAO = new UserTypeDAO(conexao);
    String[] ids = request.getParameterValues("check");            
    for (int i = 0; i < ids.length; i++) 
    {
        UserType usertype = usertypeDAO.get(Integer.parseInt(ids[i]),true);
        usertypeDAO.delete(usertype);
    }
    response.sendRedirect("list.jsp");
 %>