/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Renan
 */
@WebServlet("/SaveCalendarAccessData")
public class HandleAuthorize extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        /*
        response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                // TODO output your page here. You may use following sample code.
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet HandleAuthorize</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Servlet HandleAuthorize 111 at " + 
                        request.getContextPath() + 
                        "queryString = " + queryString + "</h1>");
                out.println("</body>");
                out.println("</html>");
            }
        */
        
        String queryString = request.getQueryString();
        if (queryString != null && !queryString.isEmpty()) {
            // salva token no banco...
        } else {
            // calendar scope: https://www.googleapis.com/auth/calendar
            // client id : 1060354502110-ho0eica6il5t0hgvcdd80iiu9ee2ve2u.apps.googleusercontent.com
            // redirect uri : urn:ietf:wg:oauth:2.0:oob
            response.sendRedirect("https://accounts.google.com/o/oauth2/auth?" +
                "scope=https://www.googleapis.com/auth/calendar&" +
                "redirect_uri=http://localhost:8084/UbipriServer/HandleAuthorize&" +
                "response_type=code&" +
                "client_id=1060354502110-ho0eica6il5t0hgvcdd80iiu9ee2ve2u.apps.googleusercontent.com");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
