package com.ds.assignment1_2.html;

import com.ds.assignment1_2.bll.FlightBLL;
import com.ds.assignment1_2.entity.City;
import com.ds.assignment1_2.entity.Flight;
import com.ds.assignment1_2.utils.Utils;

import java.util.List;

public class HtmlUtils {

    public static String createHtmlComboList(List<City> cities, String cityType){
        String list = "<select id = \"" + cityType + "\" name = \"" + cityType + "\">\n";
        for(City city: cities){
            list += "<option value=\"" + city.getName() + "\">" + city.getName() + "</option>\n";
        }

        list += "</select>\n";

        return list;
    }

    public static String getFlightsTable(FlightBLL flightBLL) {
        String table = "<table style=\"width:100%\">\n" +
                "  <tr>\n" +
                "    <th>Id</th>\n" +
                "    <th>Flight Number</th> \n" +
                "    <th>Airplane Type</th>\n" +
                "    <th>Departure City</th>\n" +
                "    <th>Departure Time</th>\n" +
                "    <th>Arrival City</th>\n" +
                "    <th>Arrival Time</th>\n" +
                "  </tr>";
        for(Flight flight: flightBLL.getFlights()){
            table += "<tr>";
            table += "<td>" + flight.getId() + "</td>";
            table += "<td>" + flight.getFlightNumber() + "</td>";
            table += "<td>" + flight.getAirplaneType() + "</td>";
            table += "<td>" + flight.getDepartureCity().getName() + "</td>";
            table += "<td>" + Utils.getTimeFromDate(flight.getDepartureTime()) + "</td>";
            table += "<td>" + flight.getArrivalCity().getName() + "</td>";
            table += "<td>" + Utils.getTimeFromDate(flight.getArrivalTime()) + "</td>";
            table += "</tr>";
        }
        return table;
    }
}
