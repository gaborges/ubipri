<%-- 
    Document   : list
    Created on : 01/04/2014, 16:24:57
    Author     : Guazzelli
--%>

<%@page import="server.model.Functionality"%>
<%@page import="server.model.Device"%>
<%@page import="server.dao.FunctionalityDAO"%>
<%@page import="server.dao.DeviceDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="server.dao.DeviceFunctionalityDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="server.model.DeviceFunctionality"%>
<%@page import="server.util.AccessBD"%>
<!DOCTYPE html>

<html>
    <head>
        <link rel="stylesheet" type="text/css" media="screen" href="../style.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Device Functionality List</title>
    </head>
    <script type="text/javascript" SRC="../Scripts.js" ></script>


    <body>
        <div class="container">
            <h1 class="titulo">Ubipri</h1>
            <nav id="menu">
                <ul>
                    <li><h2>Device Functionalities</h2>
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
            <div  class="list">
                <table  id="tablepaging">
                    <thead>
                        <tr>
                            <th><input type="checkbox" onClick="toggle(this)"/></th>
                            <th><p>Device</p></th>
                    <th><p>Functionality</p></th>
                    </tr>
                    </thead>
                    <form action="remove.jsp" method="get"  onSubmit="return confirmSubmit()">
                        <tbody>
                            <%
                                AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
                                DeviceFunctionalityDAO devfuncDAO = new DeviceFunctionalityDAO(conexao);
                                DeviceDAO deviceDAO = new DeviceDAO(conexao);
                                FunctionalityDAO funcDAO = new FunctionalityDAO(conexao);

                                ArrayList<DeviceFunctionality> functionalities = devfuncDAO.getList();

                                DeviceFunctionality devfunc;

                                for (int i = 0; i < functionalities.size(); i++) {
                                    devfunc = functionalities.get(i);

                                    Device device = deviceDAO.get(devfunc.getDeviceId(), true);
                                    Functionality func = funcDAO.get(devfunc.getFunctionalityId(), true);
                            %>
                            <tr>
                                <td class="check"><input  type="checkbox" name="check" value="<%=devfunc.getDeviceId()%>,<%=devfunc.getFunctionalityId()%>" /></td>
                                <td id="info">
                                    <%if (device.getId() != null) {%>
                                    <ul>
                                        <li><%=device.getName()%>
                                            <ul>
                                                <li><p><b>ID</b>: <%=device.getId()%></p></li>
                                                <li><p><b>Code</b>: <%=device.getCode()%></p></li>
                                                <li><p><b>Name</b>: <%=device.getName()%></p></li>
                                                <li><p><b>Type</b>: <%=device.getDeviceType().getName()%></p></li>
                                                <li><p><b>User</b>: <%=device.getUser().getUserName()%></p></li>
                                            </ul>
                                        </li>
                                    </ul>
                                    <%} else {%>
                                    Unknown
                                    <%}%>
                                </td>
                                <td><%=func.getName()%></td>
                            </tr>
                            <%}%>
                        </tbody>
                </table>
                <div id="pageNavPosition">
                </div>
                <input type="submit" value="Remove Selected" class="remove">
                </form>
                <form name="form_incluir" action="form.jsp" class="inline">
                    <input type="submit" value="New Device Functionality" name="new"/>
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
