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

//temp
import java.sql.Date;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author tonyxp
 */
public class CreateEventServlet extends HttpServlet {

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
            out.println("<title>Servlet CreateEventServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateEventServlet at " + request.getContextPath () + "</h1>");
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
        
        //get user from HttpSession
        HttpSession session = request.getSession();
        
        User user = (User) session.getAttribute("loggedInUser");
        //get group from servletContext
        ServletContext servletContext = getServletContext();
        
        Group group = (Group) servletContext.getAttribute("group");
        
        // create new event       
        Event event = new Event();
        EventDAO eventDAO = new EventDAO();
        
       //set event manager
        event.setManager(user);
        
        //set linked group
        event.setLinkedGroup(group);
        
        //set submitted event attributes
        
        int year = Integer.parseInt(request.getParameter("year"));
        int month = Integer.parseInt(request.getParameter("month"));
        int day = Integer.parseInt(request.getParameter("day"));
        int hour = Integer.parseInt(request.getParameter("hour"));
        int minute = Integer.parseInt(request.getParameter("minute"));
        
        Calendar startDateAndTime = new GregorianCalendar(year, month - 1, day, hour, minute, 0);
                // for some reason Calendar numbers months from 0 to 11
        
        event.setStartDateAndTime(startDateAndTime);
        
        //set other attributes
        
        event.setEventName(request.getParameter("eventname"));
        event.setDescription(request.getParameter("description"));
        event.setDuration(Integer.parseInt(request.getParameter("duration")));
        event.setVenue(request.getParameter("venue"));
        
        //PrintWriter printWriter = response.getWriter();
        //Date sqlDate = new java.sql.Date(event.getStartDateAndTime().getTime().getTime());
        
        //printWriter.println("SQL DATE" + sqlDate.toString());
        //printWriter.println("CALENDER DATE" + startDateAndTime.getTime().toString());
        
        
        event = eventDAO.create(event);
        
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
