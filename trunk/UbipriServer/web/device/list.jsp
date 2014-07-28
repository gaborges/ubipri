<%-- 
    Document   : list
    Created on : 01/04/2014, 16:24:57
    Author     : Guazzelli
--%>

<%@page import="server.dao.EnvironmentDAO"%>
<%@page import="server.model.Environment"%>
<%@page import="server.model.User"%>
<%@page import="server.dao.UserDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="server.dao.DeviceDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="server.model.Device"%>
<%@page import="server.util.AccessBD"%>
<!DOCTYPE html>

<html>
    <head>
        <link rel="stylesheet" type="text/css" media="screen" href="../style.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Device List</title>
    </head>
    <script type="text/javascript" SRC="../Scripts.js" ></script>


    <body>
        <div class="container">
            <h1 class="titulo">Ubipri</h1>
            <nav id="menu">
                <ul>
                    <li><h2>Devices</h2>
                        <ul>
                            <li><a href="../user/list.jsp">Users</a></li>
                            <li><a href="../device/list.jsp">Devices</a></li>
                            <li><a href="../functionality/list.jsp">Functionalities</a></li>
                            <li><a href="../environment/list.jsp">Environment</a></li>
                            <li><a href="../localization_type/list.jsp">Localization Types</a></li>
                            <li><a href="../environment_type/list.jsp">Environment Types</a></li>
                            <li><a href="../communication_type/list.jsp">Communication Types</a></li>
                            <li><a href="../device_communication/list.jsp">Devices Communications</a></li>
                            <li><a href="../device_functionality/list.jsp">Devices Functionalities</a></li>
                            <li><a href="../device_type/list.jsp">Devices Types</a></li>
                            <li><a href="../point/list.jsp">Points</a></li> 
                            <li><a href="../user_profile_environment/list.jsp">User Profile Environment</a></li>
                            <li><a href="../user_type/list.jsp">User Type</a></li>
                            <li><a href="../log_event/list.jsp">Log Event</a></li>
                        </ul>
                    </li>
                </ul>
            </nav>
            <div class="list">
                <table  id="tablepaging">
                    <thead>
                        <tr>
                            <th><input type="checkbox" onClick="toggle(this)"/></th>
                            <th><p>ID</p></th>
                    <th><p>Code</p></th>
                    <th><p>Name</p></th>
                    <th><p>Type</p></th>
                    <th><p>User</p></th>
                    <th><p>Current Environment</p></th>
                    <th><p>Update</p></th>
                    </tr>
                    </thead>
                    <form action="remove.jsp" method="get"  onSubmit="return confirmSubmit()">
                        <tbody>
                            <%
                                AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
                                DeviceDAO deviceDAO = new DeviceDAO(conexao);
                                UserDAO userDAO = new UserDAO(conexao);
                                EnvironmentDAO envDAO = new EnvironmentDAO(conexao);
                                ArrayList<Device> devices = deviceDAO.getList();

                                Device device;
                                User user;
                                Environment env;

                                for (int i = 0; i < devices.size(); i++) {
                                    device = devices.get(i);
                                    user = userDAO.get(device.getUser().getId(), false);
                                    if (device.getCurrentEnvironment().getId() != -1) {
                                        env = envDAO.get(device.getCurrentEnvironment().getId(), false);
                                    } else {
                                        env = null;
                                    }

                            %>
                            <tr>
                                <td><input  type="checkbox" name="check" value="<%=device.getId()%>" /></td>
                                <td><%=device.getId()%></td>
                                <td><%=device.getName()%></td>
                                <td><%=device.getCode()%></td>
                                <td><%=device.getDeviceType().getName()%></td>
                                <td id="info">
                                    <%if (user != null) {%>
                                    <ul>
                                        <li><%=user.getUserName()%>
                                            <ul>
                                                <li><p><b>ID</b>: <%=user.getId()%></p></li>
                                                <li><p><b>Username</b>: <%=user.getUserName()%></p></li>
                                                <li><p><b>Fullname</b>: <%=user.getFullName()%></p></li>
                                                <li><p><b>Password</b>: <%=user.getPassword()%></p></li>
                                                <li><p><b>Type</b>: <%=user.getUserType().getName()%></p></li>
                                            </ul>
                                        </li>
                                    </ul>
                                    <%} else {%>
                                    Unknown
                                    <%}%>
                                </td>
                                <td id="info">
                                    <%if (env != null) {%>
                                    <ul>
                                        <li><%=env.getName()%>
                                            <ul>
                                                <li><p><b>ID</b>: <%=env.getId()%></p></li>
                                                <li><p><b>Name</b>: <%=env.getName()%></p></li>
                                                <li><p><b>Latitude</b>: <%=env.getLatitude()%></p></li>
                                                <li><p><b>Longitude</b>: <%=env.getLongitude()%></p></li>
                                                <li><p><b>Altitude</b>: <%=env.getAltitude()%></p></li>
                                                <li><p><b>Range</b>: <%=env.getOperatingRange()%></p></li>
                                                <li><p><b>Version</b>: <%=env.getVersion()%></p></li>
                                                            <%if (env.getParentEnvironment() != null) {%>
                                                <li><p><b>Parent</b>: <%=env.getParentEnvironment().getName()%></p></li>
                                                            <%}%>
                                                <li><p><b>Environment Type</b>: <%=env.getEnvironmentType().getName()%></p></li>
                                                <li><p><b>Localization Type</b>: <%=env.getLocalizationType().getName()%></p></li>
                                            </ul>
                                        </li>
                                    </ul>
                                    <%} else {%>
                                    Unknown
                                    <%}%>
                                </td>
                                <td><a href="update.jsp?id=<%=device.getId()%>">Update</a></td>
                            </tr>
                            <%}%>
                        </tbody>
                </table>
                <div id="pageNavPosition">
                </div>
                <input type="submit" value="Remove Selected" class="remove">
                </form>
                <form name="form_incluir" action="form.jsp" class="inline">
                    <input type="submit" value="New Device" name="new"/>
                </form>
            </div>
        </div>
    </body>
    <script type="text/javascript"><!--
var pager = new Pager('tablepaging', 10);
        pager.init();
        pager.showPageNav('pager', 'pageNavPosition');
        pager.showPage(1);
    </script>

</html>
