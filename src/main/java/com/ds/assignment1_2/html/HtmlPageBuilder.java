package com.ds.assignment1_2.html;

import com.ds.assignment1_2.bll.CityBLL;
import com.ds.assignment1_2.bll.FlightBLL;

import javax.servlet.http.HttpServletRequest;

public class HtmlPageBuilder {

    public static String getUserHtml(HttpServletRequest request, FlightBLL flightBLL){
        String message = "Time is: " + request.getSession().getAttribute("response");

        String table = HtmlUtils.getFlightsTable(flightBLL);

        String page = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n" +
                "<html>" +
                "<body>" +
                "<div class=\"table\">" +
                "       Flights:" +
                "<br>" +
                table +
                "<form action=\"/user\" method=\"POST\">\n" +
                "        Id: <input type=\"text\" name=\"id\" required>\n" +
                "        <br>\n" +
                "         <input type = \"submit\" value = \"Submit\" />\n" +
                "    </form>" +
                "<br>" +
                (request.getSession().getAttribute("response") == null ? "" : message) +
                "<br>" +
                "</body>" +
                "</html>";

        return page;
    }

    public static String getAdminHtml(FlightBLL flightBLL, CityBLL cityBLL, HttpServletRequest request){
        String message = request.getSession().getAttribute("notification") + "";

        String page = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n" +
                "<html>" +
                "<body>" +
                "<div class=\"table\">" +
                "       Flights:" +
                HtmlUtils.getFlightsTable(flightBLL) +
                "</div>" +
                "       <br>" +
                "<div class=\"form\">" +
                "<form action=\"/admin\" method=\"POST\">\n" +
                "        Id: <input type=\"text\" name=\"id\">\n" +
                "        <br>\n" +
                "        Flight Number: <input type=\"text\" name=\"flightNumber\">\n" +
                "        <br>\n" +
                "        Airplane Type: <input type=\"text\" name=\"airplaneType\">\n" +
                "        <br>\n" +
                "\n" +
                "Departure city: " +
                HtmlUtils.createHtmlComboList(cityBLL.findAllCities(), "departureCity") +
                "        <br>\n" +
                "Departure time: <input type=\"time\" name=\"departureTime\"/>" +
                "        <br>\n" +
                "Arrival city: " +
                HtmlUtils.createHtmlComboList(cityBLL.findAllCities(), "arrivalCity") +
                "        <br>\n" +
                "Arrival time: <input type=\"time\" name=\"arrivalTime\"/>" +
                "        <br>\n" +
                "<input type=\"submit\" name=\"action\" value=\"Create\">\n" +
                "        <br>\n" +
                "<input type=\"submit\" name=\"action\" value=\"Update\">\n" +
                "        <br>\n" +
                "<input type=\"submit\" name=\"action\" value=\"Delete\">" +
                "    </form>" +
                "</div>"+
                (request.getSession().getAttribute("notification") == null ? "" : message) +
                "</body>" +
                "</html>";

        return page;
    }

    public static String getLoginHtml(HttpServletRequest request){
        String message = (String) request.getSession().getAttribute("message");

        String page = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n" +
                "<html>\n" +
                "   <body>\n" +
                "      <form action = \"/\" method = \"POST\">\n" +
                "         Username: <input type = \"text\" name = \"username\">\n" +
                "         <br />\n" +
                "         Password: <input type = \"password\" name = \"password\" />\n" +
                "         <br>" +
                "         <input type = \"submit\" value = \"Submit\" />\n" +
                "      </form>\n" +
                (message == null ? "" : message) +
                "   </body>\n" +
                "</html>";

        return page;
    }
}
