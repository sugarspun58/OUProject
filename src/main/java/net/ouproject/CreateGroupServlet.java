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

import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;

/**
 *
 * @author tonyxp
 */
public class CreateGroupServlet extends HttpServlet {

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
            out.println("<title>Servlet CreateGroupServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateGroupServlet at " + request.getContextPath () + "</h1>");
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
        
        HttpSession session = request.getSession();
        
        User user;
        
        user = (User) session.getAttribute("loggedInUser");
        
        if (request.getParameter("type").equals("club")) {
            //handle creation of Club
            
            Club club = new Club();
            
            club.setGroupName(request.getParameter("clubname"));
            club.setDescription(request.getParameter("description"));
            if (request.getParameter("isvirtual") != null) {
                club.setIsVirtual(true);
            }
            else {
                club.setIsVirtual(false);
            }
            club.setLocation(request.getParameter("location"));
            
            //attach user as manager of club
            club.setManager(user);
                        
            //create the club in the database
            club = club.create();
            
        }
        else if (request.getParameter("type").equals("team")) {
            //handle creation of Team
            
            Team team = new Team();
            
            team.setGroupName(request.getParameter("teamname"));
            team.setDescription(request.getParameter("description"));
            if (request.getParameter("isvirtual") != null) {
                team.setIsVirtual(true);
            } else {
                team.setIsVirtual(false);
            }
            team.setHomeVenue(request.getParameter("homevenue"));
            
            //attach user as manager of team
            team.setManager(user);
            
            //attach club as affliated club
            
            ServletContext servletContext = getServletContext();
            
            Club club = (Club) servletContext.getAttribute("club");
            
            team.setAffliatedClub(club);
            
            //create team in the database
            
            team = team.create();           
        }
        
        // redirect to manage groups page
        
        response.sendRedirect("managegroups.jsp");
        
      
        
        
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
