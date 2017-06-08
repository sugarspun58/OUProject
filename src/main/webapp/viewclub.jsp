<%-- 
    Document   : viewclub
    Created on : 10-Jul-2012, 22:53:57
    Author     : tonyxp
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="net.ouproject.User"%>
<%@page import="net.ouproject.Club"%>
<%@page import="net.ouproject.ClubDAO"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.ServletContext"%>
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
    
    // load club from passed value
    
    int groupID;
    
    Club club = new Club();
    
    ClubDAO clubDAO = new ClubDAO();
    
    groupID = Integer.parseInt(request.getParameter("groupID"));
    
    club = clubDAO.readByGroupID(groupID);
    
    //attach club to servlet context
    
    ServletContext servletContext = getServletContext();
    
    servletContext.setAttribute("club", club);
    
    
    
%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Club Page</title>
    </head>
    <body>
        <h1>View Club Page</h1>
        <table border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>Club Name</td>
                <td><%=club.getGroupName()%></td>
            </tr>
            <tr>
                <td>Description</td>
                <td><%=club.getDescription()%></td>
            </tr>
            <tr>
                <td>Location</td>
                <td><%=club.getLocation()%></td>
            </tr>
            <tr>
                <td>Is virtual</td>
                <td><%=club.getIsVirtual()%></td>
            </tr>
            <tr>
                <td>Manager</td>
                <td>
                    <%=club.getManager().getUserName()%>
                </td>
            </tr>
        </table>
            <a href="createteam.jsp">Create Affliated Team</a>
        <h2>Members of Club</h2>
        <table border="1" cellspacing="0" cellpadding="0">
        <tr>
            <td>User Name</td>
            <td>First Name</td>
            <td>Surname</td>
        </tr>
<%            
       for (User v : club.getMembers()) {
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
            if (!club.isMember(user)) {
%>
    <form name="joinGroup" action="JoinGroupServlet" method="post">
        <input type="hidden" name="groupID" value="<%=club.getGroupID()%>">
        <input type="submit" value="Join Group">
    </form>
            
<%
            } else {
%>
    <form name="leaveGroup" action="LeaveGroupServlet" method="post">
        <input type="hidden" name="groupID" value="<%=club.getGroupID()%>">
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

            for (Event v : club.getEvents()) {
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
