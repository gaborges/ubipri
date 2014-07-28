<%-- 
    Document   : updateuser
    Created on : 24/04/2014, 15:22:43
    Author     : Guazzelli
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="server.dao.LocalizationTypeDAO"%>
<%@page import="server.dao.EnvironmentTypeDAO"%>
<%@page import="server.model.EnvironmentType"%>
<%@page import="server.model.LocalizationType"%>
<%@page import="server.dao.EnvironmentDAO"%>
<%@page import="server.model.Environment"%>
<%@page import="server.util.AccessBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" media="screen" href="../style.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Environment</title>
    </head>
    <script type="text/javascript" SRC="../Scripts.js" ></script>


    <body>

        <div  class="form">
            <div>
                <h2>Update Environment</h2>
            </div>
            <div>
                <table>
                    <tbody>
                        <%
                            AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
                            EnvironmentDAO envDAO = new EnvironmentDAO(conexao);

                            Environment env1 = envDAO.get(Integer.parseInt(request.getParameter("id")), true);

                            if (request.getMethod().equals("POST")) {

                                Environment env = new Environment();

                                env.setId(Integer.parseInt(request.getParameter("id")));
                                env.setName(request.getParameter("name"));
                                env.setLatitude(Double.parseDouble(request.getParameter("latitude")));
                                env.setLongitude(Double.parseDouble(request.getParameter("longitude")));
                                env.setAltitude(Double.parseDouble(request.getParameter("altitude")));
                                env.setVersion(Integer.parseInt(request.getParameter("version")));
                                env.setParentEnvironment(new Environment(Integer.parseInt((request.getParameter("parent")))));
                                env.setEnvironmentType(new EnvironmentType(Integer.parseInt((request.getParameter("envtype")))));
                                env.setLocalizationType(new LocalizationType(Integer.parseInt((request.getParameter("loctype")))));

                                envDAO.update(env);
                                response.sendRedirect("list.jsp");
                            }

                        %>
                    <form name="submit" method="POST" onSubmit="return validaform();"> 
                        <tr>
                            <th><label>ID:</label></th>
                            <td><input type="hidden" name="id" value="<%=env1.getId()%>"><b><%=env1.getId()%></b></td>
                        </tr>
                        <tr>
                            <th><label>Name:</label></th>
                            <td><input type="text" name="name" value="<%=env1.getName()%>"/></td>
                        </tr>
                        <tr>
                            <th><label>Latitude:</label></th>
                            <td><input type="" name="latitude" value="<%=env1.getLatitude()%>"/></td>
                        </tr>
                        <tr>
                            <th><label>Longitude:</label></th>
                            <td><input type="text" name="longitude" value="<%=env1.getLongitude()%>"/></td>
                        </tr>
                        <tr>
                            <th><label>Altitude:</label></th>
                            <td><input type="text" name="altitude" value="<%=env1.getAltitude()%>"/></td>
                        </tr>
                        <tr>
                            <th><label>Operating Range:</label></th>
                            <td><input type="text" name="range" value="<%=env1.getOperatingRange()%>"/></td>
                        </tr>
                        <tr>
                            <th><label>Version:</label></th>
                            <td><input type="number" name="version" value="<%=env1.getVersion()%>"/></td>
                        </tr>
                        <tr>
                            <th><label>Parent ID:</label></th>
                            <td><select name="parent">
                                    <%
                                        ArrayList<Environment> environments = envDAO.getList();

                                        Environment envlist;

                                        for (int i = 0; i < environments.size(); i++) {
                                            envlist = environments.get(i);%>
                                    <option value="<%=envlist.getId()%>"<%if (env1.getParentEnvironment().getId() == envlist.getId()) {%>selected<%}%>><%=envlist.getName()%></option>
                                    <%}%>
                                </select></td>
                        </tr>
                        <tr>
                            <th><label>Environment Type:</label></th>
                            <td><select name="envtype">
                                    <%
                                        EnvironmentTypeDAO envtypeDAO = new EnvironmentTypeDAO(conexao);
                                        ArrayList<EnvironmentType> environmenttypes = envtypeDAO.getList();

                                        EnvironmentType envtypes;

                                        for (int i = 0; i < environmenttypes.size(); i++) {
                                            envtypes = environmenttypes.get(i);%>
                                    <option value="<%=envtypes.getId()%>"<%if (env1.getEnvironmentType().getId() == envtypes.getId()) {%>selected<%}%>><%=envtypes.getName()%></option>
                                    <%}%>
                                </select></td>
                        </tr>
                        <tr>
                            <th><label>Localization Type:</label></th>
                            <td><select name="loctype">
                                    <%
                                        LocalizationTypeDAO loctypeDAO = new LocalizationTypeDAO(conexao);
                                        ArrayList<LocalizationType> localizationtypes = loctypeDAO.getList();

                                        LocalizationType loctypes;

                                        for (int i = 0; i < localizationtypes.size(); i++) {
                                            loctypes = localizationtypes.get(i);%>
                                    <option value="<%=loctypes.getId()%>"<%if (env1.getLocalizationType().getId() == loctypes.getId()) {%>selected<%}%>><%=loctypes.getName()%></option>
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