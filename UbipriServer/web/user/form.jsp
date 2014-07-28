<%-- 
    Document   : newuser
    Created on : 01/04/2014, 17:06:57
    Author     : Guazzelli
--%>
<%@page import="server.dao.UserTypeDAO"%>
<%@page import="server.model.UserType"%>
<%@page import="java.util.ArrayList"%>
<%@page import="server.dao.UserDAO"%>
<%@page import="server.model.User"%>
<%@page import="server.util.AccessBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" media="screen" href="../style.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New User</title>
    </head>
    <script type="text/javascript" SRC="../Scripts.js" ></script>


    <body>

        <%
            AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
            UserDAO useDAO = new UserDAO(conexao);
            UserTypeDAO typeDAO = new UserTypeDAO(conexao);

            if (request.getMethod().equals("POST")) {
                User user = new User(
                        null,
                        request.getParameter("username"),
                        request.getParameter("password"),
                        request.getParameter("fullname")
                );
                user.setUserType(typeDAO.get(Integer.parseInt(request.getParameter("type")), false));

                useDAO.insert(user);
                response.sendRedirect("list.jsp");
            }
        %>    
        <div class="form">
            <div>
                <h2>New User</h2>
            </div>
            <div>
                <table align="center">
                    <tbody>
                    <form name="submit" method="POST" onSubmit="return validaform();"> 
                        </tr>
                        <tr>
                            <th>Username:</th>
                            <td><input type="text" name="username"/></td>
                        </tr>
                        <tr>
                            <th>Full Name:</th>
                            <td><input type="text" name="fullname"/></td>
                        </tr>
                        <tr>
                            <th>Password:</th>
                            <td><input type="text" name="password"/></td>
                        </tr>
                        <tr>
                            <th>Type:</th>
                            <td><select name="type">
                                    <%
                                        ArrayList<UserType> usertypes = typeDAO.getList();

                                        UserType usertype;

                                        for (int i = 0; i < usertypes.size(); i++) {
                                            usertype = usertypes.get(i);%>
                                    <option value="<%=usertype.getId()%>"><%=usertype.getName()%></option>
                                    <%}%>
                                </select></td>
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
