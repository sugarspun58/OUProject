<%-- 
    Document   : createevent
    Created on : 10-Jul-2012, 22:53:57
    Author     : tonyxp
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="net.ouproject.User"%>
<%@page import="net.ouproject.Group"%>
<%@page import="net.ouproject.GroupDAO"%>
<%@page import="javax.servlet.ServletContext"%>

<%-- get user from HttpSesson object --%>
<%
    User user;
    
    user = (User) session.getAttribute("loggedInUser");
    
    if (user == null) {
        response.sendRedirect("login.jsp");
    }
    
    //load group data 
    
    GroupDAO groupDAO = new GroupDAO();
    int groupID = Integer.parseInt(request.getParameter("groupID"));
    Group group = groupDAO.read(groupID);
    
    //Attach group to servlet context
    
    ServletContext servletContext = getServletContext();
    servletContext.setAttribute("group", group);
    
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Event Page</title>
    </head>
    <body>
        <h1>Create Event</h1>
        <h2>Group Name: <%=group.getGroupName()%></h2>
        <h2>Group ID: <%=group.getGroupID()%></h2>
        <form name="createEventForm" action="CreateEventServlet" method="post">
            <table cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <td align="right">Event Name</td>
                    <td>
                        <input type="text" name="eventname">
                    </td>
                </tr>
                <tr>
                    <td align="right">Event Description</td>
                    <td>
                        <input type="text" name="description">
                    </td>
                </tr>
                <tr>
                    <td align="right">Start Date</td>
                    <td>
                        Day<input type="text" name="day">
                        Month<input type="text" name="month">
                        Year<input type="text" name="year">
                    </td>
                </tr>
                <tr>
                    <td align="right">Start Time</td>
                    <td>
                        Hour<input type="text" name="hour">
                        Minute<input type="text" name="minute">
                    </td>
                </tr>
                <tr>
                    <td align="right">Duration (in hours)</td>
                    <td>
                        <input type="text" name="duration">
                    </td>
                </tr>
                <tr>
                    <td align="right">Venue</td>
                    <td>
                        <input type="text" name="venue">
                    </td>
                </tr>
                <tr>
                    <td>
                       <input type="submit" value="Create Event"> 
                    </td>
                </tr>
            </table>
        </form> 
    </body>
</html>
