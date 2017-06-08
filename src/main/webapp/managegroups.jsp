<%-- 
    Document   : managegroups
    Created on : 10-Jul-2012, 22:53:57
    Author     : tonyxp
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="net.ouproject.User"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.util.ArrayList"%>
<%@page import="net.ouproject.Group"%>

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
        <title>Manage Groups Page</title>
    </head>
    <body>
        <h1>Manage Groups Page</h1>
        <h2>Groups Managed</h2>
        <table border="1" cellpadding="0" cellspacing="0">
            <tr>
                <td>GroupID</td>
                <td>Group Name</td>
                <td>Group Description</td>
                <td>Is virtual?</td>
                <td>Type</td>
                <td>Actions</td>
            </tr>            
            
<%
    ArrayList<Group> groups = new ArrayList<Group>();
    
    groups = user.getManagerOf();
    
    for (Group v : groups) {
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
                
<% 
    if (v.getType().equals("club")) {
%>
                <td><a href="editclub.jsp?groupID=<%=v.getGroupID()%>">Edit</a></td>
<%
   } else if (v.getType().equals("team")) {
%>          
                <td><a href="editteam.jsp?groupID=<%=v.getGroupID()%>">Edit</a><td>
<%
   }
%>               
            </tr>        

<%         
   }
          
%>
        </table>

        <a href="createclub.jsp">Create New Club</a>
        
        
        
    </body>
</html>
