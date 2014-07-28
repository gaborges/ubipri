
<%@page import="server.model.Functionality"%>
<%@page import="server.dao.FunctionalityDAO"%>
<%-- 
    Document   : newuser
    Created on : 01/04/2014, 17:06:57
    Author     : Guazzelli
--%>
<%@page import="server.dao.DeviceFunctionalityDAO"%>
<%@page import="server.model.DeviceFunctionality"%>
<%@page import="server.util.AccessBD"%>
<%@page import="java.util.ArrayList"%>
<%@page import="server.model.Device"%>
<%@page import="server.dao.DeviceDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" media="screen" href="../style.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Device Functionality</title>
    </head>
    <script type="text/javascript" SRC="../Scripts.js" ></script>


    <body>
        <%
            AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");

            DeviceFunctionalityDAO funcDAO = new DeviceFunctionalityDAO(conexao);

            if (request.getMethod().equals("POST")) {
                DeviceFunctionality devfunc = new DeviceFunctionality();
                devfunc.setDeviceId(Integer.parseInt(request.getParameter("device_id")));
                devfunc.setFunctionalityId(Integer.parseInt(request.getParameter("functionality_id")));

                funcDAO.insert(devfunc);
                response.sendRedirect("list.jsp");
            }
        %> 
        <div class="form">
            <div>
                <h2>New Device Functionality</h2>

                <div>
                    <table align="center">
                        <tbody>
                        <form name="submit" method="post" onSubmit="return validaform();"> 
                            <tr>
                                <th><label>Device:</label></th>
                                <td><select name="device_id">
                                        <%
                                            DeviceDAO deviceDAO = new DeviceDAO(conexao);

                                            ArrayList<Device> devices = deviceDAO.getList();

                                            Device device;

                                            for (int i = 0; i < devices.size(); i++) {
                                                device = devices.get(i);%>
                                        <option value="<%=device.getId()%>"><%=device.getName()%></option>
                                        <%}%>
                                    </select></td>
                            </tr>
                            <tr>
                                <th><label>Functionality:</label></th>
                                <td><select name="functionality_id">
                                        <%FunctionalityDAO envDAO = new FunctionalityDAO(conexao);

                                            ArrayList<Functionality> functionalities = envDAO.getList();

                                            Functionality func;

                                            for (int i = 0; i < functionalities.size(); i++) {
                                                func = functionalities.get(i);%>
                                        <option value="<%=func.getId()%>"><%=func.getName()%></option>
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
