<%-- 
    Document   : editteam
    Created on : 10-Jul-2012, 22:53:57
    Author     : tonyxp
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="net.ouproject.User"%>
<%@page import="net.ouproject.Team"%>
<%@page import="net.ouproject.TeamDAO"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.ServletContext"%>
<%@page import="net.ouproject.Event"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%
    User user;
    
    if (null == session.getAttribute("loggedInUser")) { //TO-DO this is not working, throws NPE
        response.sendRedirect("login.jsp"); 
    }    
    
    user = (User) session.getAttribute("loggedInUser");

    //load team data
    Team team = new Team();
    TeamDAO teamDAO = new TeamDAO();
    
    int groupID = Integer.parseInt(request.getParameter("groupID"));
    
    team = teamDAO.readByGroupID(groupID);
    
    if (!(team.getManager().getUserName().equals(user.getUserName()))) {
        response.sendRedirect("error.jsp");
    }
    
    //attach Club to the servlet context to pass across requests
    
    ServletContext servletContext = getServletContext();
    
    servletContext.setAttribute("team", team);
    
    
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Team Page</title>
    </head>
    <body>
        <h1>Edit Team Page</h1>
        <h2>Editing group ID <%=team.getGroupID()%></h2>
        <h2>Club ID <%=team.getTeamID()%></h2>
        <form name="editTeamForm" action="UpdateTeamServlet" onSubmit="" method="post">
            <input type="hidden" name="teamID" value="<%=team.getTeamID()%>">
            <table cellspacing="0" cellpadding="0" border="0">
                <tr>
                    <td align="right">Team (Group) Name</td>
                    <td>
                        <input type="text" name="groupname" value="<%=team.getGroupName()%>">
                    </td>
                </tr>
                <tr>
                    <td align="right">Description</td>
                    <td>
                        <input type="text" name="description" value="<%=team.getDescription()%>">
                    </td>
                </tr>
                <tr>
                    <td align="right">Is virtual?</td>
<%
    if (team.getIsVirtual()) {
%>
                    <td>
                        <input type="checkbox" name="isvirtual" value="true" checked>
                    </td>
<%  
    } else {
%>
                    <td>
                        <input type="checkbox" name="isvirtual" value="true">
                    </td>    
<%
    }
%>                  
                </tr>
                <tr>
                    <td align="right">Home venue</td>
                    <td>
                        <input type="text" name="homevenue" value="<%=team.getHomeVenue()%>">
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="submit" value="Update">
                    </td>
                </tr>
            </table>
        </form>
        <form name="deleteTeam" action="DeleteTeamServlet" onsubmit="" method="post">
            <input type="hidden" name="teamID" value="<%=team.getTeamID()%>">
            <p>Enter password and click Delete to delete Team</p>
            <input type="password" name="password">
            <input type="submit" value="Delete">
        </form>
        <h2>Linked Events</h2>
        <table border="1" cellpadding="0" cellspacing="0">
            <tr>
                <td>Event ID</td>
                <td>Event Name</td>
                <td>Date</td>
                <td>Start Time</td>
                <td>Durtation</td>
                <td>Is Cancelled</td>
                <td>Edit</td>
            </tr>
<%      
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat timeFormat = new SimpleDateFormat("HH:mm");

            for (Event v : team.getEvents()) {
%>
            <tr>
                <td><%=v.getEventID()%></td>
                <td><%=v.getEventName()%></td>
                <td><%=dateFormat.format(v.getStartDateAndTime().getTime().getTime())%></td>
                <td><%=timeFormat.format(v.getStartDateAndTime().getTime().getTime())%></td>
                <td><%=v.getDuration()%> Hours</td>
                <td>
<%
                if (v.getIsCancelled()) {
%>                  
                    EVENT CANCELLED
<%
                }
%>
                </td>
                <td><a href="editevent.jsp?eventID=<%=v.getEventID()%>">Edit Event</a></td>
            </tr>
<%          
            }            
%>            
        </table>
        <a href="createevent.jsp?groupID=<%=team.getGroupID()%>">Create New Event</a>
    </body>
</html>
