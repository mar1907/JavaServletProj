package com.ds.assignment1_2.entity.builder;

import com.ds.assignment1_2.entity.City;
import com.ds.assignment1_2.entity.Flight;

import java.util.Date;

public class FlightBuilder {

    private Flight flight;

    public FlightBuilder() {
        flight = new Flight();
    }

    public FlightBuilder setId(int id){
        flight.setId(id);
        return this;
    }

    public FlightBuilder setFlightNumber(String flightNumber){
        flight.setFlightNumber(flightNumber);
        return this;
    }

    public FlightBuilder setAirplaneType(String airplaneType){
        flight.setAirplaneType(airplaneType);
        return this;
    }

    public FlightBuilder setDepartureCity(City departureCity){
        flight.setDepartureCity(departureCity);
        return this;
    }

    public FlightBuilder setDepartureTime(Date departureTime){
        flight.setDepartureTime(departureTime);
        return this;
    }

    public FlightBuilder setArrivalCity(City arrivalCity){
        flight.setArrivalCity(arrivalCity);
        return this;
    }

    public FlightBuilder setArrivalTime(Date arrivalTime){
        flight.setArrivalTime(arrivalTime);
        return this;
    }

    public Flight build(){
        return flight;
    }
}
