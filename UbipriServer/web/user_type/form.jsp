<%-- 
    Document   : newuser
    Created on : 01/04/2014, 17:06:57
    Author     : Guazzelli
--%>
<%@page import="server.dao.UserTypeDAO"%>
<%@page import="server.model.UserType"%>
<%@page import="server.util.AccessBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" media="screen" href="../style.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New User Type</title>
    </head>    
    <script type="text/javascript" SRC="../Scripts.js" ></script>

    <body>

        <%
            AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
            UserTypeDAO usertypeDAO = new UserTypeDAO(conexao);

            if (request.getMethod().equals("POST")) {
                UserType usertype = new UserType();                
                usertype.setName(request.getParameter("name"));

                usertypeDAO.insert(usertype);
                response.sendRedirect("list.jsp");
            }
        %>    
        <div class="form">
            <div>
                <h1>New User Type</h1>
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
