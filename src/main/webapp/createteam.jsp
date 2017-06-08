<%-- 
    Document   : createteam
    Created on : 10-Jul-2012, 22:53:57
    Author     : tonyxp
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="net.ouproject.User"%>
<%@page import="net.ouproject.Club"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.ServletContext"%>
<%-- get user from HttpSesson object --%>
<%
    User user;
    
    user = (User) session.getAttribute("loggedInUser");
    
    if (user == null) {
        response.sendRedirect("login.jsp");
    }
    
    // load club from servlet context
    
    ServletContext servletContext = getServletContext();
    
    Club club = (Club) servletContext.getAttribute("club");
    
    
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Team Page</title>
    </head>
    <body>
        <h1>Create Team Page</h1>
        <h3>Affliated Club: <%=club.getGroupName()%> 
            (Club ID: <%=club.getClubID()%>) 
            (Group ID: <%=club.getGroupID()%>)
        </h3>
        <form name="createTeamForm" action="CreateGroupServlet" method="post">
            <input type="hidden" name="type" value="team">
            <input type="hidden" name="clubID" value="<%=club.getClubID()%>">
            <table cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <td align="right">Team Name</td>
                    <td>
                        <input type="text" name="teamname">
                    </td>
                </tr>
                <tr>
                    <td align="right">Description</td>
                    <td>
                        <input type="text" name="description">                        
                    </td>
                </tr>
                <tr>
                    <td align="right">Is virtual?</td>
                    <td>
                        <input type="checkbox" name="isvirtual" value="true">
                    </td>
                </tr>
                <tr>
                    <td align="right">Home Venue</td>
                    <td>
                        <input type="text" name="homevenue">
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
