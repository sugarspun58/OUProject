<%-- 
    Document   : viewprofile
    Created on : 10-Jul-2012, 22:53:57
    Author     : tonyxp
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="net.ouproject.User"%>
<%@page import="net.ouproject.Group"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="net.ouproject.Event"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>

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
        <title>View Profile Page</title>
    </head>
    <body>
        <h1>My Profile</h1>
        <table border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td><img height="240" width="320" src="/OUProject/ImageServlet?imageName=<%=user.getUserName()%>"></td>
                <td></td>
            </tr>
            <tr>
                <td>Username</td>
                <td><%=user.getUserName()%></td>
            </tr>
            <tr>
                <td>First Name</td>
                <td><%=user.getFirstName()%></td>
            </tr>
            <tr>
                <td>Surname</td>
                <td><%=user.getSurname()%></td>
            </tr>
            <tr>
                <td>Sex</td>
                <td><%=user.getSex()%></td>
            </tr>
            <tr>
                <td>Email Address</td>
                <td><%=user.getEmail()%></td>                
            </tr>
            <tr>
                <td>Location</td>
                <td><%=user.getLocation()%></td>
            </tr>
            <tr>
                <td>Country</td>
                <td><%=user.getCountry()%></td>
            </tr>
            <tr>
                <td>Linked Login Password</td>
                <td><%=user.getLinkedLogin().getPassword()%></td>
            </tr>
        </table>
            <h2>My Group Memberships</h2>
        <table border="1" cellspacing="0" cellpadding="0">
            <tr>
                <td>Group Name</td>
                <td>Group type</td>
                <td>Group Manager</td>
            </tr>
<%
        for (Group v : user.getMemberOf()) {
%>
            <tr>
<%
            if (v.getType().equals("team")) {
%>
                <td><a href="viewteam.jsp?groupID=<%=v.getGroupID()%>"><%=v.getGroupName()%></a></td>
<%
            } else if (v.getType().equals("club")) {
%>
                <td><a href="viewclub.jsp?groupID=<%=v.getGroupID()%>"><%=v.getGroupName()%></a></td>
<%
           }
%>
                <td><%=v.getType()%></td>
                <td><%=v.getManager().getUserName()%></td>
            </tr>
<%
       }
%>                                  
        </table>
        <h2>Events you are currently available for</h2>
        <table border="1" cellspacing="0" cellpadding="0">
            <tr>
                <td>Event Name</td>
                <td>Time and Date</td>
                <td>Group Name</td>
            </tr>
<%
        for (Event v : user.getAvailableForEvents()) {
%>
            <tr>
                <td><a href="viewevent.jsp?eventID=<%=v.getEventID()%>"><%=v.getEventName()%></a></td>
<%
            DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
%>
                <td><%=dateFormat.format(v.getStartDateAndTime().getTime().getTime())%></td>
                <td><%=v.getLinkedGroup().getGroupName()%></td>
            </tr>
<%
       }
%>
        </table>
            <a href="editprofile.jsp">Edit Profile</a><br>
            <a href="managegroups.jsp">Manage My Groups</a><br>
            <a href="searchgroup.jsp">Search for Groups</a><br>
            <a href="pendingpartnerrequest.jsp">Pending Partner Requests</a>
    </body>
</html>
