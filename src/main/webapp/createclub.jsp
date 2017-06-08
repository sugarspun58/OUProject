<%-- 
    Document   : createclub
    Created on : 10-Jul-2012, 22:53:57
    Author     : tonyxp
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="net.ouproject.User"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%-- get user from HttpSesson object --%>
<%
    User user;
    
    user = (User) session.getAttribute("loggedInUser");
    
    if (user == null) {
        response.sendRedirect("login.jsp");
    }
    
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Club Page</title>
    </head>
    <body>
        <h1>Create Club Page</h1>
        <form name="createClubForm" action="CreateGroupServlet" method="post">
            <input type="hidden" name="type" value="club">
            <table cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <td align="right">Club Name</td>
                    <td>
                        <input type="text" name="clubname">
                    </td>
                </tr>
                <tr>
                    <td align="right">Description</td>
                    <td>
                        <input type="text" name="description">
                    </td>
                </tr>
                <tr>
                    <td align="right">Is Virtual?</td>
                    <td>
                        <input type="checkbox" name="isvirtual" value="true">
                    </td>
                </tr>
                <tr>
                    <td align="right">Location</td>
                    <td>
                        <input type="text" name="location">
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="submit" value="Create">
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
