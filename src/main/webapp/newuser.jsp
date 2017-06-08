<%-- 
    Document   : newuser
    Created on : 09-Jul-2012, 18:33:18
    Author     : tonyxp
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create New User</title>
        <script type="text/javascript">
// javascript to check validation of form  

function validateForm() {
    var returnval;
    //check for null entries
    var element = document.forms["createProfileForm"]["username"].value;
    if (element == null || element == "") {
        alert("Username field is blank");
        returnval = false;
    }
    var element = document.forms["createProfileForm"]["password1"].value;
    if (element == null || element == "") {
        alert("Password field is blank");
        returnval = false;
    }
    var element = document.forms["createProfileForm"]["password2"].value;
    if (element == null || element == "") {
        alert("Repeat Password field is blank");
        returnval = false;
    }
    var element = document.forms["createProfileForm"]["firstname"].value;
    if (element == null || element == "") {
        alert("First Name field is blank");
        returnval = false;
    }
    var element = document.forms["createProfileForm"]["surname"].value;
    if (element == null || element == "") {
        alert("Surname field is blank");
        returnval = false;
    }
    var element = document.forms["createProfileForm"]["email1"].value;
    if (element == null || element == "") {
        alert("Email Address field is blank");
        returnval = false;
    }
    var element = document.forms["createProfileForm"]["email2"].value;
    if (element == null || element == "") {
        alert("Repeat Email field is blank");
        returnval = false;
    }
    var element = document.forms["createProfileForm"]["location"].value;
    if (element == null || element == "") {
        alert("Location field is blank");
        returnval = false;
    }
    var element = document.forms["createProfileForm"]["country"].value;
    if (element == null || element == "") {
        alert("Country field is blank");
        returnval = false;
    }
    // check that emails match
    var email1 = document.forms["createProfileForm"]["email1"].value;
    var email2 = document.forms["createProfileForm"]["email2"].value;
    if (email1 != email2) {
        alert("Email addresses do not match");
        returnval = false;
    }
     // check that passwords match
    var password1 = document.forms["createProfileForm"]["password1"].value;
    var password2 = document.forms["createProfileForm"]["password2"].value;
    if (password1 != password2) {
        alert("Passwords do not match");
        returnval = false;
    }
    return returnval;
}
   
        </script>
    </head>
    <body>
        <h1>Create New User</h1>
        <form name="createProfileForm" action="CreateProfileServlet"
              onSubmit="return validateForm()" method="post">
            <table cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <td align="right">Username</td>
                    <td>
                        <input type="text" name="username">                        
                    </td>
                </tr>
                <tr>
                    <td align="right">Password</td>
                    <td>
                        <input type="password" name="password1">
                    </td>
                </tr>
                <tr>
                    <td align="right">Repeat Password</td>
                    <td>
                        <input type="password" name="password2">
                    </td>
                </tr>
                <tr>
                    <td align="right">First Name</td>
                    <td>
                        <input type="text" name="firstname">
                    </td>
                </tr>
                <tr>
                    <td align="right">Surname</td>
                    <td>
                        <input type="text" name="surname">
                    </td>
                </tr>
                <tr>
                    <td align="right">Email Address</td>
                    <td>
                        <input type="text" name="email1">
                    </td>
                </tr>
                <tr>
                    <td align="right">Repeat Email</td>
                    <td>
                        <input type="text" name="email2">
                    </td>
                </tr>
                <tr>
                    <td align="right">Sex</td>
                    <td>
                        <input type="radio" name="sex" value="M" checked="checked"> Male <br>
                        <input type="radio" name="sex" value="F"> Female
                    </td>
                </tr>
                <tr>
                    <td align="right">Location</td>
                    <td>
                        <input type="text" name="location">
                    </td>
                </tr>
                <tr>
                    <td align="right">Country</td>
                    <td>
                        <input type="text" name="country">
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <input type="submit" value="Submit">
                    </td>
                </tr>
                
                
            </table>
        </form>
        
    </body>
</html>
