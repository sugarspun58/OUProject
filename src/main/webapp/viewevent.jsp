<%-- 
    Document   : viewevent
    Created on : 10-Jul-2012, 22:53:57
    Author     : tonyxp
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="net.ouproject.User"%>
<%@page import="net.ouproject.Event"%>
<%@page import="net.ouproject.EventDAO"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.ServletContext"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="net.ouproject.PartnerRequestDAO"%>

<%
    User user;
    
    user = (User) session.getAttribute("loggedInUser");
    
    if (user == null) {
        response.sendRedirect("login.jsp");
    }
    
    // load event from passed value
    
    int eventID;
    
    Event event = new Event();
    
    EventDAO eventDAO = new EventDAO();
    
    eventID = Integer.parseInt(request.getParameter("eventID"));
    
    event = eventDAO.read(eventID);
    
    //attach event to servlet context
    
    ServletContext servletContext = getServletContext();
    
    servletContext.setAttribute("event", event);
    
    //boolean value holding if user is already available for event
    
    Boolean isAvailable = false;
    
    //check to see if user is available for event
    
    for (User v : event.getUsersAvailable()) {
        
        if (user.getUserName().equals(v.getUserName())) {
            isAvailable = true;
        }
        
    }
    
    PartnerRequestDAO partnerRequestDAO = new PartnerRequestDAO();
    
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Event Page</title>
    </head>
    <body>
        <h1>View Event</h1>
        <table border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>Event ID</td>
                <td><%=event.getEventID()%></td>
            </tr>
            <tr>
                <td>Event Name</td>
                <td><%=event.getEventName()%></td>
            </tr>
            <tr>
                <td>Date and Time</td>
<%
            DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
%>
                
                <td><%=dateFormat.format(event.getStartDateAndTime().getTime().getTime())%> </td>
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
            <tr>
                <td>Linked Group</td>
                <td><%=event.getLinkedGroup().getGroupName()%></td>
            </tr>
            <tr>
                <td>Manager</td>
                <td><%=event.getManager().getUserName()%></td>
            </tr>
        </table>
<%
        if (isAvailable) {
%>
        <h3>You are currently available for this event</h3>
<%      }
%>
        <h3>Users available for partnership for event</h3>
        <table border ="1" cellpadding="0" cellspacing="0">
            <tr>
                <td>User Name</td>
                <td>First Name</td>
                <td>Surname</td>
                <td>REQUEST PARTNERSHIP</td>
            </tr>
<%
        for (User v : event.getUsersAvailable()) {
            if (!v.getUserName().equals(user.getUserName())) {
%>
            <tr>
                <td><%=v.getUserName()%></td>
                <td><%=v.getFirstName()%></td>
                <td><%=v.getSurname()%></td>
<%                
                if (partnerRequestDAO.doesRequestExist(user.getUserName(), v.getUserName(), event.getEventID())) {
%>                
                <td>Request Pending</td>
                
<%
               } else if (v.getUserName().equals(user.getUserName())) {
%> 
                <td></td>
<%
               } else {
%>               
                <td>
                    <form action="CreatePartnerRequestServlet" name="CreatePartnerRequestForm" method="post">
                        <input type="hidden" name="requestee" value="<%=v.getUserName()%>">
                        <input type="submit" value="Request Partnership">
                    </form>
                    
                </td>
<%
              } 
%>
            </tr>    
<%
       
            }
        }
%>
        </table>
<% 
        if (!isAvailable) {
%>
        <form action="AvailableForServlet" name="isAvailableForm" method="post">
            <input type="hidden" name="action" value="add">
            <input type="submit" value="Make Me Available for this Event">
        </form>
<%
       
       } else {
%>
        <form action="AvailableForServlet" name="isAvailableForm" method="post">
            <input type="hidden" name="action" value="remove">
            <input type="submit" value="Remove me from being available for this Event">
        </form>
<%
       }
%>
    </body>
</html>
