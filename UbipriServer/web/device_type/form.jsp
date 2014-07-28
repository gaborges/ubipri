<%-- 
    Document   : newuser
    Created on : 01/04/2014, 17:06:57
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
        <title>New Device Type</title>
    </head>
    <script type="text/javascript" SRC="../Scripts.js" ></script>

    <body>

        <%
            AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
            DeviceTypeDAO devtypeDAO = new DeviceTypeDAO(conexao);

            if (request.getMethod().equals("POST")) {
                DeviceType devtype = new DeviceType(
                        null,
                        request.getParameter("name"));

                devtypeDAO.insert(devtype);
                response.sendRedirect("list.jsp");
            }
        %>    
        <div class="form">
            <div>
                <h1>New Device Type</h1>
            </div>
            <div>
                <table align="center">
                    <tbody>
                    <form name="submit" method="POST" onSubmit="return validaform();"> 
                        <tr>
                            <th><label>Name:</label></th>
                            <td><input type="text" name="name"/></td>
                        </tr>
                        <tr>
                            <td style="text-align: right;"><input type="submit" value="Include"/></td>
                    </form>
                    <form action="list.jsp">
                        <td><input type="submit" value="Cancel" name="cancelar"/></td>
                        </tr>   
                    </form>                        
                    </tbody>
                </table>
            </div>
            <div id="mensagem"></div>
        </div>
    </body>
</html>
