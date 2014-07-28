<%-- 
    Document   : newuser
    Created on : 01/04/2014, 17:06:57
    Author     : Guazzelli
--%>
<%@page import="server.dao.LocalizationTypeDAO"%>
<%@page import="server.dao.EnvironmentTypeDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="server.dao.EnvironmentDAO"%>
<%@page import="server.model.LocalizationType"%>
<%@page import="server.model.EnvironmentType"%>
<%@page import="server.model.Environment"%>
<%@page import="server.util.AccessBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" media="screen" href="../style.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Environment</title>
    </head>

    <script type="text/javascript" SRC="../Scripts.js" ></script>


    <body>

        <%
            AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
            EnvironmentDAO envDAO = new EnvironmentDAO(conexao);

            if (request.getMethod().equals("POST")) {
                Environment env = new Environment();

                env.setName(request.getParameter("name"));
                env.setLatitude(Double.parseDouble(request.getParameter("latitude")));
                env.setLongitude(Double.parseDouble(request.getParameter("longitude")));
                env.setAltitude(Double.parseDouble(request.getParameter("altitude")));
                env.setOperatingRange(Double.parseDouble(request.getParameter("range")));
                env.setVersion(Integer.parseInt(request.getParameter("version")));
                env.setParentEnvironment(new Environment(Integer.parseInt((request.getParameter("parent")))));
                env.setEnvironmentType(new EnvironmentType(Integer.parseInt((request.getParameter("envtype")))));
                env.setLocalizationType(new LocalizationType(Integer.parseInt((request.getParameter("loctype")))));

                envDAO.insert(env);
                response.sendRedirect("list.jsp");
            }
        %>    
        <div class="form">
            <div>
                <h2>New Environment</h2>
            </div>
            <div>
                <table>
                    <tbody>
                    <form name="submit" method="POST" onSubmit="return validaform();"> 
                        <tr>
                            <th><label>Name:</label></th>
                            <td><input type="text" name="name"/></td>
                        </tr>
                        <tr>
                            <th><label>Latitude:</label></th>
                            <td><input type="" name="latitude"/></td>
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
                            <th><label>Operating Range:</label></th>
                            <td><input type="text" name="range"/></td>
                        </tr>
                        <tr>
                            <th><label>Version:</label></th>
                            <td><input type="number" name="version"/></td>
                        </tr>
                        <tr>
                            <th><label>Parent ID:</label></th>
                            <td><select name="parent">
                                    <%
                                        ArrayList<Environment> environments = envDAO.getList();

                                        Environment envlist;

                                        for (int i = 0; i < environments.size(); i++) {
                                            envlist = environments.get(i);%>
                                    <option value="<%=envlist.getId()%>"><%=envlist.getName()%></option>
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
                                    <option value="<%=envtypes.getId()%>"><%=envtypes.getName()%></option>
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
                                    <option value="<%=loctypes.getId()%>"><%=loctypes.getName()%></option>
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
