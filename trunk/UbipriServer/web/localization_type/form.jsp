<%-- 
    Document   : newuser
    Created on : 01/04/2014, 17:06:57
    Author     : Guazzelli
--%>
<%@page import="server.dao.LocalizationTypeDAO"%>
<%@page import="server.model.LocalizationType"%>
<%@page import="server.util.AccessBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" media="screen" href="../style.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New LocalizationType</title>
    </head>
    <script type="text/javascript" SRC="../Scripts.js" ></script>


    <body>

        <%
            AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
            LocalizationTypeDAO locDAO = new LocalizationTypeDAO(conexao);

            if (request.getMethod().equals("POST")) {
                LocalizationType loc = new LocalizationType(
                        null,
                        request.getParameter("name"),
                        Double.parseDouble(request.getParameter("precision")),
                        request.getParameter("metric"));

                locDAO.insert(loc);
                response.sendRedirect("list.jsp");
            }
        %>    
        <div class="form">
            <div>
                <h2>New Localization Type</h2>
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
                            <th><label>Precision:</label></th>
                            <td><input type="text" name="precision"/></td>
                        </tr>
                        <tr>
                            <th><label>Metric:</label></th>
                            <td><input type="text" name="metric"/></td>
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
