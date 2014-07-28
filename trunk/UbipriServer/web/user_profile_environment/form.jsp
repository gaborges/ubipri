<%-- 
    Document   : newuser
    Created on : 01/04/2014, 17:06:57
    Author     : Guazzelli
--%>
<%@page import="server.dao.UserProfileEnvironmentDAO"%>
<%@page import="server.model.UserProfileEnvironment"%>
<%@page import="server.util.AccessBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" media="screen" href="../style.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New User Profile Environment</title>
    </head>
    <script type="text/javascript" SRC="../Scripts.js" ></script>


    <body>

        <%
            AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
            UserProfileEnvironmentDAO useproenvDAO = new UserProfileEnvironmentDAO(conexao);

            if (request.getMethod().equals("POST")) {
                UserProfileEnvironment useproenv = new UserProfileEnvironment();
                useproenv.setName(request.getParameter("name"));

                useproenvDAO.insert(useproenv);
                response.sendRedirect("list.jsp");
            }
        %>    
        <div class="form">
            <div>
                <h1>New User Profile Environment</h1>
            </div>
            <div>
                <table align="center">
                    <tbody>
                    <form name="submit" method="POST" onSubmit="return validaform();"> 
                        <tr>
                            <th><label>Name:</label></th>
                            <td><input type="text" name="name"/></td>
                        </tr>
                        </tbody>
                </table>
            </div>
            <div  style="margin-top: 20px;">
                <table>
                    <tr>
                    <input type="submit" value="Include"/>
                    </form>
                    <form action="list.jsp">
                        <input type="submit" value="Cancel" name="cancelar" />
                        </tr>   
                    </form>
                </table>
                <div id="mensagem"></div>
            </div>
        </div>
    </body>
</html>
