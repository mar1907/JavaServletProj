package com.ds.assignment1_2.servlet;

import com.ds.assignment1_2.bll.FlightBLL;
import com.ds.assignment1_2.bll.FlightBLLImplementation;
import com.ds.assignment1_2.dao.CityDAO;
import com.ds.assignment1_2.dao.FlightDAO;
import com.ds.assignment1_2.html.HtmlPageBuilder;
import com.ds.assignment1_2.utils.Notification;
import org.hibernate.cfg.Configuration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserServlet extends HttpServlet {

    private FlightBLL flightBLL;

    @Override
    public void init() throws ServletException {
        super.init();
        flightBLL = new FlightBLLImplementation(new FlightDAO(new Configuration().configure().buildSessionFactory()),
                new CityDAO(new Configuration().configure().buildSessionFactory()));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!request.getSession().getAttribute("admin").equals(0)){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Notification<String> notification = new Notification<>();

        try{
            notification = flightBLL.getLocalTimeForArrivalCity(
                    Integer.parseInt(request.getParameter("id")));
        } catch (NumberFormatException e){
            notification.addError("Please enter an integer number in id field");
        }

        request.getSession().setAttribute("response",
                notification.hasErrors() ? notification.getFormattedErrors() : notification.getResult());

        response.sendRedirect("/user");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!request.getSession().getAttribute("admin").equals(0)){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println(
                HtmlPageBuilder.getUserHtml(request, flightBLL)
        );
    }
}
