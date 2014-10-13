<%-- 
    Document   : index
    Created on : Oct 12, 2014, 3:31:51 PM
    Author     : Renan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form name="frm" method="post" action="SaveCalendarAccessData">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="12%">&nbsp;</td>
                    <td width="88%">&nbsp;</td>
                </tr>
                <tr>
                    <td>Nome do usuário</td>
                    <td><input type="text" name="userName" ></td>
                </tr>
                <tr>
                    <td>Senha</td>
                    <td><input type="password" name="password"></td>
                </tr>
                <tr>
                    <td>Email</td>
                    <td><input type="email" name="email" width="80%"></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td><input type="submit" name="submit" value="Verificar"></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
            </table>
        </form>
        <table align="left" border="3">
            <tr>
                <td><form action='SaveCalendarAccessData'><input type="submit" value="Verificar" name="Verificar"/></form></td>
            </tr>
        </table>
    </body>
</html>
