<%-- 
    Document   : viewteam
    Created on : 10-Jul-2012, 22:53:57
    Author     : tonyxp
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="net.ouproject.User"%>
<%@page import="net.ouproject.Team"%>
<%@page import="net.ouproject.TeamDAO"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.ServletContext"%>
<%@page import="java.util.ArrayList"%>
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
    
    // load team from passed value
    
    int groupID;
    
    Team team = new Team();
    
    TeamDAO teamDAO = new TeamDAO();
    
    groupID = Integer.parseInt(request.getParameter("groupID"));
    
    team = teamDAO.readByGroupID(groupID);
    
    //attach team to servlet context
    
    ServletContext servletContext = getServletContext();
    
    servletContext.setAttribute("team", team);
    
    
    
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Team Page</title>
    </head>
    <body>
        <h1>View Team Page</h1>
        <table border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>Team Name</td>
                <td><%=team.getGroupName()%></td>
            </tr>
            <tr>
                <td>Description</td>
                <td><%=team.getDescription()%></td>
            </tr>
            <tr>
                <td>Home venue</td>
                <td><%=team.getHomeVenue()%></td>
            </tr>
            <tr>
                <td>Is virtual?</td>
                <td><%=team.getIsVirtual()%></td>
            </tr>
            <tr>
                <td>Affliated Club</td>
                <td><%=team.getAffliatedClub().getGroupName()%></td>
            </tr>
            <tr>
                <td>Manager</td>
                <td>
                    <%=team.getManager().getUserName()%>
                </td>
            </tr>
        </table>
    <h2>Members of Team</h2>
    <table border="1" cellspacing="0" cellpadding="0">
        <tr>
            <td>User Name</td>
            <td>First Name</td>
            <td>Surname</td>
        </tr>
<%            
       for (User v : team.getMembers()) {
%>            
        <tr>
            <td><%=v.getUserName()%></td>
            <td><%=v.getFirstName()%></td>
            <td><%=v.getSurname()%></td>
        </tr>
<%
       }
%>      
    </table>
                  
        
<%            
            if (!team.isMember(user)) {
%>
    <form name="joinGroup" action="JoinGroupServlet" method="post">
        <input type="hidden" name="groupID" value="<%=team.getGroupID()%>">
        <input type="submit" value="Join Group">
    </form>
            
<%
            } else {
%>
    <form name="leaveGroup" action="LeaveGroupServlet" method="post">
        <input type="hidden" name="groupID" value="<%=team.getGroupID()%>">
        <input type="submit" value="Leave Group">
    </form>
<%          
            }
%>  
    <h2>Linked Events</h2>
        <table border="1" cellpadding="0" cellspacing="0">
            <tr>
                <td>Event ID</td>
                <td>Event Name</td>
                <td>Date</td>
                <td>Start Time</td>
                <td>Durtation</td>
                <td>Is Cancelled</td>
            </tr>
<%      
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat timeFormat = new SimpleDateFormat("HH:mm");

            for (Event v : team.getEvents()) {
%>
            <tr>
                <td><%=v.getEventID()%></td>
                <td><a href="viewevent.jsp?eventID=<%=v.getEventID()%>"><%=v.getEventName()%></a></td>
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
            </tr>
<%          
            }            
%>            
        </table>
    </body>
</html>
