<%-- 
    Document   : editprofile
    Created on : 10-Jul-2012, 22:53:57
    Author     : tonyxp
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="net.ouproject.User"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>


<%

    User user;
    
    if (null == session.getAttribute("loggedInUser")) { //TO-DO this is not working, throws NPE
        response.sendRedirect("login.jsp"); 
    }    
    
    user = (User) session.getAttribute("loggedInUser");

%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Profile</title>
    </head>
    <body>
        <h1>Edit My Profile</h1>
        <form name="editProfileForm" action="UpdateProfileServlet" onSubmit="" method="post">
            <table cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <td align="right">First Name</td>
                    <td>
                        <input type="text" name="firstname" value="<%=user.getFirstName()%>">
                    </td>
                </tr>
                <tr>
                    <td align="right">Surname</td>
                    <td>
                        <input type="text" name="surname" value="<%=user.getSurname()%>">
                    </td>
                </tr>
                <tr>
                    <td align="right">Email Address</td>
                    <td>
                        <input type="text" name="email" value="<%=user.getEmail()%>">
                    </td>
                </tr>
                <tr>
                    <td align="right">Sex</td>
                    <td>
                    <%  
                        char c = 'M';
                        if (user.getSex() == c) { 
                    %>    
                        <input type="radio" name="sex" value="M" checked="checked"> Male <br>
                        <input type="radio" name="sex" value="F"> Female
                    <%  }
                        else {
                    %>        
                        <input type="radio" name="sex" value="M"> Male <br>
                        <input type="radio" name="sex" value="F" checked="checked"> Female                      
                    <%
                        }
                    %>
                    </td>
                </tr>
                <tr>
                    <td align="right">Location</td>
                    <td>
                        <input type="text" name="location" value="<%=user.getLocation()%>">
                    </td>
                </tr>
                <tr>
                    <td align="right">Country</td>
                    <td>
                        <input type="text" name="country" value="<%=user.getCountry()%>">
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <input type="submit" value="Update Changes">
                    </td>
                </tr>
            </table>
        </form>
        <form name="deleteProfile" action="DeleteProfileServlet" method="post">
            <table cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <td>Enter password to delete profile</td>
                    <td>
                        <input type="password" name="password">
                    </td>
                </tr>    
                <tr>
                    <td></td>
                    <td>
                        <input type="submit" value="Delete Profile">
                    </td>
                </tr>            
            </table>
        </form>
        <form name="uploadProfilePicture" action="UploadProfilePicture" enctype="multipart/form-data" method="post">
            <input type="file" name="uploadFile">
            <input type="submit" value="Upload Picture">
        </form>            
    </body>
</html>
