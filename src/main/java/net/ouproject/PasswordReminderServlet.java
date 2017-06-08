/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ouproject;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author tonyxp
 */
public class PasswordReminderServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PasswordReminderServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PasswordReminderServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
             */
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        LoginDAO loginDAO = new LoginDAO();
        Login login;
        User user;
        EmailEntity emailEntity = new EmailEntity();
        String email;
        String subject;
        String body;
        
        
        String username = request.getParameter("username");
        
        login = loginDAO.read(username);
        
        if (login != null) {
            user = login.loadLinkedUser();
            email = user.getEmail();
            subject = "Password Reminder";
            body = "Your username is " + login.getUserName() +
                    ". Your password is " + login.getPassword();
            emailEntity.setRecipient(email);
            emailEntity.setSubject(subject);
            emailEntity.setBody(body);
            String outcome = emailEntity.sendMail();
            
            if (outcome == "success") {
                PrintWriter printWriter = response.getWriter();
                printWriter.println("<p>");
                printWriter.println("Email sent");
                printWriter.println("</p>");
            }
            else {
                PrintWriter printWriter = response.getWriter();
                printWriter.println("<p>");
                printWriter.println("Error sending email");
                printWriter.println("</p>");
            }
            
        }
        else {
            PrintWriter printWriter = response.getWriter();
            printWriter.println("<p>");
            printWriter.println("User not found");
            printWriter.println("</p>");
        }
        
        
        
        
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
