<%-- 
    Document   : pendingpartnerrequest
    Created on : 10-Jul-2012, 22:53:57
    Author     : tonyxp
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="net.ouproject.User"%>
<%@page import="net.ouproject.PartnerRequest"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>

<%-- get user from HttpSesson object --%>
<%
    User user;
    
    user = (User) session.getAttribute("loggedInUser");
    
    if (user == null) {
        response.sendRedirect("login.jsp");
    }
    
    DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
    
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pending Partner Requests Page</title>
    </head>
    <body>
        <h1>Pending Partner Requests</h1>
        <table cellpadding="0" cellspacing="0" border="1">
            <tr>
                <td>User requesting Partnership</td>
                <td>Event Name</td>
                <td>Linked Group Name</td>
                <td>Event Time and Date</td>
                <td>Accept or Decline Request</td>
            </tr>
<%
            for (PartnerRequest v : user.getPendingPartnerRequests()) {
%>              
            <tr>
                <td><%=v.getRequester().getUserName()%></td>
                <td><%=v.getLinkedEvent().getEventName()%></td>
                <td><%=v.getLinkedEvent().getLinkedGroup().getGroupName()%></td>
                <td><%=dateFormat.format(v.getLinkedEvent().getStartDateAndTime().getTime().getTime())%></td>
                <td>
<%
                if (v.getAccepted()) {
%>                    
                    You have accepted this request
<%
                } else {
%>
                    <form action="ManagePartnerRequestServlet" name="acceptRequestForm" method="post">
                        <input type="hidden" name="partnerRequestID" value="<%=v.getPartnerRequestID()%>">
                        <input type="hidden" name="action" value="accept">
                        <input type="submit" value="Accept Request">
                    </form>
                    <form action="ManagePartnerRequestServlet" name="declineRequestForm" method="post">
                        <input type="hidden" name="partnerRequestID" value="<%=v.getPartnerRequestID()%>">
                        <input type="hidden" name="action" value="decline">
                        <input type="submit" value="Decline Request">
                    </form>     
<%
               }
%>
                </td>
            </tr>    
<%
           }
%>
        </table>
        <h2>Status of Partner Requests you have made</h2>
        <table cellpadding="0" cellspacing="0" border="1">
            <tr>
                <td>User subject of request</td>
                <td>Event Name</td>
                <td>Linked Group Name</td>
                <td>Event Time and Date</td>
                <td>Status</td>
            </tr>
<%
            for (PartnerRequest v : user.getMadePartnerRequests()) {
%>          
            <tr>
                <td><%=v.getRequestee().getUserName()%></td>
                <td><%=v.getLinkedEvent().getEventName()%></td>
                <td><%=v.getLinkedEvent().getLinkedGroup().getGroupName()%></td>
                <td><%=dateFormat.format(v.getLinkedEvent().getStartDateAndTime().getTime().getTime())%></td>
                <td>
<%
                if (v.getAccepted()) {
%>                  
                    REQUEST HAS BEEN ACCEPTED
<%
               } else {
%>  
                    Request Pending
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
