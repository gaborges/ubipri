<%-- 
    Document   : updateuser
    Created on : 24/04/2014, 15:22:43
    Author     : Guazzelli
--%>

<%@page import="server.dao.DeviceTypeDAO"%>
<%@page import="server.model.DeviceType"%>
<%@page import="server.util.AccessBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" media="screen" href="../style.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Device Type</title>
    </head>

        <script type="text/javascript" SRC="../Scripts.js" ></script>



    <body>

        <div  class="form">
            <div>
                <h2>Update Device Type</h2>
            </div>
            <div>
                <table>
                    <tbody>
                        <%
                            AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
                            DeviceTypeDAO devtypeDAO = new DeviceTypeDAO(conexao);

                            DeviceType devtype1 = devtypeDAO.get(Integer.parseInt(request.getParameter("id")), true);

                            if (request.getMethod().equals("POST")) {

                                DeviceType devtype = new DeviceType(
                                        Integer.parseInt(request.getParameter("id")),
                                        request.getParameter("name"));

                                devtypeDAO.update(devtype);
                                response.sendRedirect("list.jsp");
                            }

                        %>
                    <form name="submit" method="POST" onSubmit="return validaform();"> 
                        <tr>
                            <th><label>ID:</label></th>
                            <td><input type="hidden" name="id" value="<%=devtype1.getId()%>"><b><%=devtype1.getId()%></b></td>
                        </tr>
                        <tr>
                            <th><label>Name:</label></th>
                            <td><input type="text" name="name" value="<%=devtype1.getName()%>"/></td>
                        </tr>
                        <tr>
                        <td class="accept"><input type="submit" value="Atualizar"/></td>
                    </form>
                    <form action="list.jsp">
                        <td><input type="submit" value="Cancelar" name="cancelar" /></td>
                        </tr>   
                    </form>
                    </tbody>
                </table>
            </div>
            <div id="mensagem"></div>
        </div>
    </body>
</html>