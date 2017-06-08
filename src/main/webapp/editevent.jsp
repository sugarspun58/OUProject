<%-- 
    Document   : editevent
    Created on : 10-Jul-2012, 22:53:57
    Author     : tonyxp
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="net.ouproject.User"%>
<%@page import="net.ouproject.Event"%>
<%@page import="net.ouproject.EventDAO"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.ServletContext"%>
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

    //load event data
    Event event = new Event();
    EventDAO eventDAO = new EventDAO();
    
    int eventID = Integer.parseInt(request.getParameter("eventID"));
    
    event = eventDAO.read(eventID);
    
    if (!(event.getManager().getUserName().equals(user.getUserName()))) {
        response.sendRedirect("error.jsp");
    }
    
    //attach Event to the servlet context to pass across requests
    
    ServletContext servletContext = getServletContext();
    
    servletContext.setAttribute("event", event);
    
    
%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Event Page</title>
    </head>
    <body>
        <h1>Edit Event Page (Cancel or Delete Event)</h1>
        <table cellspacing="0" cellpadding="0" border="0">
            <tr>
                <td>Event ID</td>
                <td><%=event.getEventID()%></td>
            </tr>
            <tr>
                <td>Event Name</td>
                <td><%=event.getEventName()%></td>
            </tr>
            <tr>
                <td>Linked Group</td>
                <td><%=event.getLinkedGroup().getGroupName()%></td>
            </tr>
            <tr>
                <td>Start Date and Time</td>
<%
            DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
%>
                
                
                <td><%=dateFormat.format(event.getStartDateAndTime().getTime().getTime())%></td>
            </tr>
            <tr>
                <td>Description</td>
                <td><%=event.getDescription()%></td>
            </tr>
            <tr>
                <td>Duration</td>
                <td><%=event.getDuration()%> Hours</td>
            </tr>
            <tr>
                <td>Venue</td>
                <td><%=event.getVenue()%></td>
            </tr>
        </table>
<%
        if (event.getIsCancelled()) {
%>
        <h3>EVENT CANCELLED</h3>
        <p>Reason: <%=event.getCancelReason()%></p>
<%
       } else {
%>
        <form name="cancelEventForm" action="CancelEventServlet" method="post">
            <input type="text" name="cancelreason">
            <input type="submit" value="Cancel Event">
        </form>
<%
       }
%>               
        <form name="deleteEventForm" action="DeleteEventServlet" method="post">
            Enter your password and click Delete to delete event
            <input type="password" name="password">
            <input type="submit" value="Delete Event">
        </form>
    </body>
</html>
