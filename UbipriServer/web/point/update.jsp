<%-- 
    Document   : updateuser
    Created on : 24/04/2014, 15:22:43
    Author     : Guazzelli
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="server.dao.EnvironmentDAO"%>
<%@page import="server.model.Environment"%>
<%@page import="server.dao.PointDAO"%>
<%@page import="server.model.Point"%>
<%@page import="server.util.AccessBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" media="screen" href="../style.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Point</title>
    </head>
    <script type="text/javascript" SRC="../Scripts.js" ></script>



    <body>

        <div  class="form">
            <div>
                <h2>Update Point</h2>
            </div>
            <div>
                <table>
                    <tbody>
                        <%
                            AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
                            PointDAO pointDAO = new PointDAO(conexao);

                            Point point1 = pointDAO.get(Integer.parseInt(request.getParameter("id")), true);

                            if (request.getMethod().equals("POST")) {
                                Point point = new Point(
                                        Double.parseDouble(request.getParameter("latitude")),
                                        Double.parseDouble(request.getParameter("longitude")),
                                        Double.parseDouble(request.getParameter("altitude")),
                                        Integer.parseInt(request.getParameter("order")),
                                        new Environment(Integer.parseInt(request.getParameter("environment"))));
                                point.setId(point1.getId());

                                pointDAO.update(point);
                                response.sendRedirect("list.jsp");
                            }

                        %>
                    <form name="submit" method="POST" onSubmit="return validaform();"> 
                        <tr>
                            <th><label>ID:</label></th>
                            <td><input type="hidden" name="id" value="<%=point1.getId()%>"><b><%=point1.getId()%></b></td>
                        </tr>
                        <tr>
                            <th><label>Latitude:</label></th>
                            <td><input type="text" name="latitude" value="<%=point1.getLatitude()%>"/></td>
                        </tr>
                        <tr>
                            <th><label>Longitude:</label></th>
                            <td><input type="text" name="longitude" value="<%=point1.getLongitude()%>"/></td>
                        </tr>
                        <tr>
                            <th><label>Altitude:</label></th>
                            <td><input type="text" name="altitude" value="<%=point1.getAltitude()%>"/></td>
                        </tr>
                        <tr>
                            <th><label>Order:</label></th>
                            <td><input type="text" name="order" value="<%=point1.getOrder()%>"/></td>
                        </tr>
                        <tr>
                            <th><label>Environment:</label></th>
                            <td><select name="environment" value="<%=point1.getEnvironment().getId()%>">
                                    <%
                                        EnvironmentDAO environmentDAO = new EnvironmentDAO(conexao);

                                        ArrayList<Environment> envs = environmentDAO.getList();

                                        Environment env;

                                        for (int i = 0; i < envs.size(); i++) {
                                            env = envs.get(i);%>
                                    <option value="<%=env.getId()%>"<%if (point1.getEnvironment().getId() == env.getId()) {%>selected<%}%>><%=env.getName()%></option>
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