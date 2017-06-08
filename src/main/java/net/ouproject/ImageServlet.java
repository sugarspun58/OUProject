/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ouproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.File;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletOutputStream;

/**
 *
 * @author tonyxp
 */
public class ImageServlet extends HttpServlet {

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
            out.println("<title>Servlet ImageServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ImageServlet at " + request.getContextPath () + "</h1>");
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
        
        String filename = "C:/Documents and Settings/tonyxp/My Documents/NetBeansProjects/OUProject/uploaded/" 
                + request.getParameter("imageName") + "/profileimage.jpg";
        
        File file = new File(filename);
        
        response.setContentType("image/jpeg");
        
        response.setContentLength((int)file.length());
        
        FileInputStream in = new FileInputStream(file);
        OutputStream out = response.getOutputStream();
        
        byte[] buf = new byte[1024];
        int count = 0;
        
        while ((count = in.read(buf)) >= 0) {
            
            out.write(buf, 0, count);
            
        }
        in.close();
        out.close();
        
        
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
