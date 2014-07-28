<%-- 
    Document   : newuser
    Created on : 01/04/2014, 17:06:57
    Author     : Guazzelli
--%>
<%@page import="server.dao.UserDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="server.dao.DeviceDAO"%>
<%@page import="server.model.Device"%>
<%@page import="server.model.DeviceType"%>
<%@page import="server.dao.DeviceTypeDAO"%>
<%@page import="server.model.User"%>
<%@page import="server.util.AccessBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" media="screen" href="../style.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Device</title>
    </head>
    <script type="text/javascript" SRC="../Scripts.js" ></script>


    <body>

        <%
            AccessBD conexao = new AccessBD("localhost", "ubipri", "postgres", "postgres");
            DeviceDAO deviceDAO = new DeviceDAO(conexao);

            if (request.getMethod().equals("POST")) {
                Device device = new Device();
                device.setName(request.getParameter("name"));
                device.setCode(request.getParameter("code"));
                device.setDeviceType(new DeviceType());
                device.getDeviceType().setId(Integer.parseInt(request.getParameter("type")));
                device.setUser(new User());
                device.getUser().setId(Integer.parseInt(request.getParameter("user")));

                deviceDAO.insert(device);
                response.sendRedirect("list.jsp");
            }
        %>    
        <div class="form">
            <div>
                <h2>New Device</h2>
            </div>
            <div>
                <table align="center">
                    <tbody>
                    <form name="submit" method="POST" onSubmit="return validaform();"> 
                        <tr>
                            <th><label>Name:</label></th>
                            <td><input type="text" name="name"/></td>
                        </tr>
                        <tr>
                            <th><label>Code:</label></th>
                            <td><input type="text" name="code"/></td>
                        </tr>
                        <tr>
                            <th><label>Type:</label></th><td>
                                <select name="type">
                                    <%
                                        DeviceTypeDAO devtypeDAO = new DeviceTypeDAO(conexao);
                                        ArrayList<DeviceType> devtypes = devtypeDAO.getList();

                                        DeviceType devtype;

                                        for (int i = 0; i < devtypes.size(); i++) {
                                            devtype = devtypes.get(i);%>
                                    <option value="<%=devtype.getId()%>"><%=devtype.getName()%></option>
                                    <%}%>
                                </select></td>
                        </tr>
                        <tr>
                            <th><label>User:</label></th><td>
                                <select name="user">
                                    <%
                                        UserDAO userDAO = new UserDAO(conexao);
                                        ArrayList<User> users = userDAO.getList();

                                        User user;

                                        for (int i = 0; i < users.size(); i++) {
                                            user = users.get(i);%>
                                    <option value="<%=user.getId()%>"><%=user.getUserName()%></option>
                                    <%}%>
                                </select></td>
                        </tr>
                        <tr>
                            <td class="accept"><input type="submit" value="Include"/></td>
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
