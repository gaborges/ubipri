<%-- 
    Document   : updateuser
    Created on : 24/04/2014, 15:22:43
    Author     : Guazzelli
--%>
<%@page import="server.model.UserType"%>
<%@page import="java.util.ArrayList"%>
<%@page import="server.dao.UserTypeDAO"%>
<%@page import="server.dao.UserDAO"%>
<%@page import="server.model.User"%>
<%@page import="server.util.AccessBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" media="screen" href="../style.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update User</title>
    </head>
    <script type="text/javascript" SRC="../Scripts.js" ></script>



    <body>

        <div  class="form">
            <div>
                <h2>Update User</h2>
            </div>
            <div>
                <table>
                    <tbody>
                        <%
                            AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
                            UserDAO useDAO = new UserDAO(conexao);
                            UserTypeDAO typeDAO = new UserTypeDAO(conexao);

                            User user1;
                            String param = request.getParameter("id");
                            int ids = Integer.parseInt(param);
                            user1 = useDAO.get(ids, true);

                            if (request.getMethod().equals("POST")) {
                                User user = new User(
                                        ids,
                                        request.getParameter("username"),
                                        request.getParameter("password"),
                                        request.getParameter("fullname"));
                                
                                user.setUserType(typeDAO.get(Integer.parseInt(request.getParameter("type")), false));
                                useDAO.update(user);
                                response.sendRedirect("list.jsp");
                            }

                        %>
                    <form name="submit" method="POST" onSubmit="return validaform();"> 
                        <tr>
                            <th><label>ID:</label></th>
                            <td><input type="hidden" name="id" value="<%=user1.getId()%>"><b><%=user1.getId()%></b></td>
                        </tr>
                        <tr>
                            <th><label>Username:</label></th>
                            <td><input type="text" name="username" value="<%=user1.getUserName()%>"/></td>
                        </tr>
                        <tr>
                            <th><label>Password:</label></th>
                            <td><input type="text" name="password" value="<%=user1.getPassword()%>" /></td>
                        </tr>
                        <tr>
                            <th><label>Fullname:</label></th>
                            <td><input type="text" name="fullname" value="<%=user1.getFullName()%>"/></td>
                        </tr>
                        <tr>
                            <th><label>Type:</label></th>
                            <td><select name="type">
                                    <%
                                        ArrayList<UserType> usertypes = typeDAO.getList();

                                        UserType usertype;

                                        for (int i = 0; i < usertypes.size(); i++) {
                                            usertype = usertypes.get(i);%>
                                    <option value="<%=usertype.getId()%>"<%if (user1.getUserType().getId() == usertype.getId()) {%>selected<%}%>><%=usertype.getName()%></option>
                                    <%}%>
                                </select></td>
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