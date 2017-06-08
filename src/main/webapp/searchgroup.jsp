<%-- 
    Document   : searchgroup
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
        <title>Search For Group Page</title>
    </head>
    <body>
        <h1>Search For Group</h1>
        <form name="searchGroupForm" action="SearchGroupServlet" method="post">
            <table cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <td align="right">Group Name</td>
                    <td>
                        <input type="text" name="groupname">
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="submit" value="Search">
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
