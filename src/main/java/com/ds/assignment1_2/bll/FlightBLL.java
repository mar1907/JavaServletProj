package com.ds.assignment1_2.bll;

import com.ds.assignment1_2.utils.Notification;
import com.ds.assignment1_2.entity.Flight;

import java.util.List;

public interface FlightBLL {

    List<Flight> getFlights();

    Notification<Boolean> addFlight(Flight flight, String departureCity, String arrivalCity);

    Notification<Boolean> updateFlight(Flight flight, String departureCity, String arrivalCity);

    Notification<Boolean> deleteFlight(int id);

    Notification<String> getLocalTimeForArrivalCity(int flightId);

}
