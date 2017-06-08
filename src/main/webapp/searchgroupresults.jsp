<%-- 
    Document   : searchgroupresults
    Created on : 10-Jul-2012, 22:53:57
    Author     : tonyxp
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="net.ouproject.User"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.util.ArrayList"%>
<%@page import="net.ouproject.Group"%>
<%@page import="javax.servlet.ServletContext"%>

<%-- get user from HttpSesson object --%>
<%
    User user;
    
    user = (User) session.getAttribute("loggedInUser");
    
    if (user == null) {
        response.sendRedirect("login.jsp");
    }
    
    ServletContext servletContext = getServletContext();
    
    ArrayList<Group> searchResults = (ArrayList<Group>) servletContext.getAttribute("searchResults");
    
    
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Groups page</title>
    </head>
    <body>
        <h1>Search Groups Results</h1>
        <table border="1" cellpadding="0" cellspacing="0">
            <tr>
                <td>Group ID</td>
                <td>Group Name</td>
                <td>Group Description</td>
                <td>Is Virtual?</td>
                <td>Type</td>                
            </tr>
<%
    for (Group v : searchResults) {
%>
            <tr>
                <td><%=v.getGroupID()%></td>
<%
        if (v.getType().equals("club")) {
%>
                <td><a href="viewclub.jsp?groupID=<%=v.getGroupID()%>"><%=v.getGroupName()%></a></td>
<%
        } else if (v.getType().equals("team")) {
%>
                <td><a href="viewteam.jsp?groupID=<%=v.getGroupID()%>"><%=v.getGroupName()%></a></td>
<%
        } 
%>                
                <td><%=v.getDescription()%></td>
                <td><%=v.getIsVirtual()%></td>
                <td><%=v.getType()%></td>
            </tr>
<%
    }      
%>
        </table> 
    </body>
</html>
