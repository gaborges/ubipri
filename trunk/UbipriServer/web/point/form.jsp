<%-- 
    Document   : newuser
    Created on : 01/04/2014, 17:06:57
    Author     : Guazzelli
--%>
<%@page import="server.model.Environment"%>
<%@page import="java.util.ArrayList"%>
<%@page import="server.dao.EnvironmentDAO"%>
<%@page import="server.dao.PointDAO"%>
<%@page import="server.model.Point"%>
<%@page import="server.util.AccessBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" media="screen" href="../style.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Point</title>
    </head>
    <script type="text/javascript" SRC="../Scripts.js" ></script>


    <body>

        <%
            AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
            PointDAO pointDAO = new PointDAO(conexao);

            if (request.getMethod().equals("POST")) {
                Point point = new Point(
                        Double.parseDouble(request.getParameter("latitude")),
                        Double.parseDouble(request.getParameter("longitude")),
                        Double.parseDouble(request.getParameter("altitude")),
                        Integer.parseInt(request.getParameter("order")),
                        new Environment(Integer.parseInt(request.getParameter("environment"))));

                pointDAO.insert(point);
                response.sendRedirect("list.jsp");
            }
        %>    
        <div class="form">
            <div>
                <h2>New Point</h2>
            </div>
            <div>
                <table align="center">
                    <tbody>
                    <form name="submit" method="POST" onSubmit="return validaform();"> 
                        <tr>
                            <th><label>Latitude:</label></th>
                            <td><input type="text" name="latitude"/></td>
                        </tr>
                        <tr>
                            <th><label>Longitude:</label></th>
                            <td><input type="text" name="longitude"/></td>
                        </tr>
                        <tr>
                            <th><label>Altitude:</label></th>
                            <td><input type="text" name="altitude"/></td>
                        </tr>
                        <tr>
                            <th><label>Order:</label></th>
                            <td><input type="text" name="order"/></td>
                        </tr>
                        <tr>
                            <th><label>Environment:</label></th>
                            <td><select name="environment">
                                    <%
                                        EnvironmentDAO environmentDAO = new EnvironmentDAO(conexao);

                                        ArrayList<Environment> envs = environmentDAO.getList();

                                        Environment env;

                                        for (int i = 0; i < envs.size(); i++) {
                                            env = envs.get(i);%>
                                    <option value="<%=env.getId()%>"><%=env.getName()%></option>
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
