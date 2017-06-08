<%-- 
    Document   : forgotpassword.jsp
    Created on : 10-Jul-2012, 22:53:57
    Author     : tonyxp
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forgotten Password Page</title>
        <script type="text/javascript">
            
function validateForm() {
    var returnval;
    
    var element = document.forms["passwordReminder"]["username"].value;
    if (element == null || element =="") {
        alert("Username field is blank");
        returnval = false;
    }
    return returnval;
}            
            
        </script>
    </head>
    <body>
        <h1>Password Reminder</h1>
        <form name="passwordReminder" action="PasswordReminderServlet" 
              onSubmit="return validateForm()" method="post">
            <table cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <td align="right">Enter Username</td>
                    <td>
                         <input type="text" name="username">
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="submit" value="Send Reminder">
                    </td> 
                </tr>
            </table>
        </form>
    </body>
</html>
