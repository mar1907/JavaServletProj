package com.ds.assignment1_2.servlet;

import com.ds.assignment1_2.bll.CityBLL;
import com.ds.assignment1_2.bll.CityBLLImplementation;
import com.ds.assignment1_2.bll.FlightBLL;
import com.ds.assignment1_2.bll.FlightBLLImplementation;
import com.ds.assignment1_2.dao.CityDAO;
import com.ds.assignment1_2.dao.FlightDAO;
import com.ds.assignment1_2.entity.Flight;
import com.ds.assignment1_2.entity.builder.FlightBuilder;
import com.ds.assignment1_2.entity.validator.FlightValidator;
import com.ds.assignment1_2.html.HtmlPageBuilder;
import com.ds.assignment1_2.utils.Notification;
import org.hibernate.cfg.Configuration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AdminServlet extends HttpServlet {

    private FlightBLL flightBLL;
    private CityBLL cityBLL;

    @Override
    public void init() throws ServletException {
        super.init();
        flightBLL = new FlightBLLImplementation(new FlightDAO(new Configuration().configure().buildSessionFactory()),
                new CityDAO(new Configuration().configure().buildSessionFactory()));

        cityBLL = new CityBLLImplementation(new CityDAO(new Configuration().configure().buildSessionFactory()));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if(!request.getSession().getAttribute("admin").equals(1)){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        Notification<Boolean> notification = new Notification<>();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        String action = request.getParameter("action");
        if ("Create".equals(action)) {

            notification = create(request, notification, sdf);

        } else if ("Update".equals(action)) {

            notification = update(request, notification, sdf);

        } else if ("Delete".equals(action)) {
            try {
                notification = flightBLL.deleteFlight(Integer.parseInt(request.getParameter("id")));
            } catch (NumberFormatException e) {
                notification.addError("Please enter an integer number in id field");
            }
        }

        request.getSession().setAttribute("notification",
                notification.hasErrors() ? notification.getFormattedErrors() : notification.getResult());

        response.sendRedirect("/admin");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if(!request.getSession().getAttribute("admin").equals(1)){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(
                HtmlPageBuilder.getAdminHtml(flightBLL, cityBLL, request)
        );
    }

    private Notification<Boolean> update(HttpServletRequest request, Notification<Boolean> notification, SimpleDateFormat sdf) {
        try{
            Flight flight = new FlightBuilder()
                    .setId(Integer.parseInt(request.getParameter("id")))
                    .setFlightNumber(request.getParameter("flightNumber"))
                    .setAirplaneType(request.getParameter("airplaneType"))
                    .setDepartureTime(sdf.parse(request.getParameter("departureTime")))
                    .setArrivalTime(sdf.parse(request.getParameter("arrivalTime")))
                    .build();

            notification = flightBLL.updateFlight(flight, request.getParameter("departureCity"),
                    request.getParameter("arrivalCity"));
        } catch (ParseException e){
            notification.addError("Please enter an integer number in id field");
        }
        return notification;
    }

    private Notification<Boolean> create(HttpServletRequest request, Notification<Boolean> notification, SimpleDateFormat sdf) {
        try{
            Flight flight = new FlightBuilder()
                    .setFlightNumber(request.getParameter("flightNumber"))
                    .setAirplaneType(request.getParameter("airplaneType"))
                    .setDepartureTime(sdf.parse(request.getParameter("departureTime")))
                    .setArrivalTime(sdf.parse(request.getParameter("arrivalTime")))
                    .build();

            notification = flightBLL.addFlight(flight, request.getParameter("departureCity"),
                    request.getParameter("arrivalCity"));
        } catch (ParseException e){
            notification.addError("Please enter an integer number in id field");
        }
        return notification;
    }
}
