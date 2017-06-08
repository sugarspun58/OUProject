<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <h1>Login Page</h1>
        <form name="login" action="LoginServlet" method="post">
            <table cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <td align="right">Username</td>
                    <td>
                        <input type="text" name="username">
                    </td>
                </tr>
                <tr>
                    <td align="right">Password</td>
                    <td>
                        <input type="password" name="password">
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <input type="submit" value="Log in">
                    </td>
                </tr>
            </table>
        </form>
        <a href="newuser.jsp">New User?</a>
        <a href="forgotpassword.jsp">Forgotten Password?</a>
    </body>
</html>
