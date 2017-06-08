/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ouproject;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author tonyxp
 * Description - Servlet models Use Case Manage Profile #1
 */
@WebServlet(name = "CreateProfileServlet", urlPatterns = {"/CreateProfileServlet"})
public class CreateProfileServlet extends HttpServlet {

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
            out.println("<title>Servlet CreateProfileServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateProfileServlet at " + request.getContextPath () + "</h1>");
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
        
        User user = new User();
        UserDAO userDAO = new UserDAO();
        Login login = new Login();
        LoginDAO loginDAO = new LoginDAO();
                
        String username = request.getParameter("username");
        
        // for temporay feedback
        PrintWriter printWriter;
                
        //check username is not in use
        
        if (loginDAO.checkAvailability(username)) {
            
            // get entered data
            String password = request.getParameter("password1");
            String firstName = request.getParameter("firstname");
            String surname = request.getParameter("surname");
            String email = request.getParameter("email1");
            char sex = request.getParameter("sex").charAt(0);
            String location = request.getParameter("location");
            String country = request.getParameter("country");
            
            // populate login object
            
            login.setUserName(username);
            login.setPassword(password);
            
            // populate user object
            
            user.setUserName(username);
            user.setFirstName(firstName);
            user.setSurname(surname);
            user.setEmail(email);
            user.setSex(sex);
            user.setLocation(location);
            user.setCountry(country);
            
            //commit to DB via DAO objects
            
          
            login = loginDAO.create(login);
            user = userDAO.create(user);
            
            //TO-DO create profile object
            
            
            // temporay feedback 
            try {
                
                //temp code providing feedback that (hopefully) database is updated
                printWriter = response.getWriter();
                printWriter.println("<p>");
                printWriter.println("Database updated");
                printWriter.println("</p>");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            
            
            
            
            
        }
        else {
            try {
                
                //temp code providing feedback that username is not available
                printWriter = response.getWriter();
                printWriter.println("<p>");
                printWriter.println("Username not available");
                printWriter.println("</p>");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet to create a new profile - Use Case Manage Profile #1";
    }// </editor-fold>
}
