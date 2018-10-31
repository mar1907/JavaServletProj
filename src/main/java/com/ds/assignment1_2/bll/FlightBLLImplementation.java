package com.ds.assignment1_2.bll;

import com.ds.assignment1_2.entity.validator.FlightValidator;
import com.ds.assignment1_2.utils.Notification;
import com.ds.assignment1_2.dao.CityDAO;
import com.ds.assignment1_2.dao.FlightDAO;
import com.ds.assignment1_2.entity.Flight;
import com.ds.assignment1_2.utils.Utils;
import com.ds.assignment1_2.webservice.LocalTime;

import java.util.Date;
import java.util.List;

public class FlightBLLImplementation implements FlightBLL {

    private FlightDAO flightDAO;
    private CityDAO cityDAO;

    public FlightBLLImplementation(FlightDAO flightDAO, CityDAO cityDAO) {
        this.flightDAO = flightDAO;
        this.cityDAO = cityDAO;
    }

    @Override
    public List<Flight> getFlights() {
        return flightDAO.findFlights();
    }

    @Override
    public Notification<Boolean> addFlight(Flight flight, String departureCity, String arrivalCity) {
        Notification<Boolean> notification = new Notification<>();

        FlightValidator validator = new FlightValidator(flight);

        if(!validator.validate()){
            validator.getErrors().forEach(notification::addError);
            notification.setResult(false);
            return notification;
        }

        flight.setDepartureCity(cityDAO.findCityByName(departureCity));
        flight.setArrivalCity(cityDAO.findCityByName(arrivalCity));

        Flight result = flightDAO.addFlight(flight);

        if(result == null){
            notification.addError("Could not add flight.");
            notification.setResult(false);
        } else {
            notification.setResult(true);
        }


        return notification;
    }

    @Override
    public Notification<Boolean> updateFlight(Flight flight, String departureCity, String arrivalCity) {
        Notification<Boolean> notification = new Notification<>();

        FlightValidator validator = new FlightValidator(flight);

        if(!validator.validate()){
            validator.getErrors().forEach(notification::addError);
            notification.setResult(false);
            return notification;
        }

        flight.setDepartureCity(cityDAO.findCityByName(departureCity));
        flight.setArrivalCity(cityDAO.findCityByName(arrivalCity));

        if(flightDAO.updateFlight(flight)==null){
            notification.addError("Could not update flight.");
            notification.setResult(false);
        } else {
            notification.setResult(true);
        }

        return notification;
    }

    @Override
    public Notification<Boolean> deleteFlight(int id) {
        Notification<Boolean> notification = new Notification<>();

        if(flightDAO.deleteFlight(id)==null){
            notification.addError("No such flight");
            notification.setResult(false);
        } else {
            notification.setResult(true);
        }

        return notification;
    }

    @Override
    public Notification<String> getLocalTimeForArrivalCity(int flightId) {
        Notification<String> result = new Notification<>();

        Flight flight = flightDAO.findFlight(flightId);

        try {
            Date departureCityTime = LocalTime.getTimeIn(flight.getDepartureCity());
            Date arrivalCityTime = LocalTime.getTimeIn(flight.getArrivalCity());

            Long timeDifference = arrivalCityTime.getTime() - departureCityTime.getTime();

            Date localTime = new Date(flight.getArrivalTime().getTime() + timeDifference);

            result.setResult(Utils.getTimeFromDate(localTime));
        } catch (Exception e) {
            result.addError("Could not find time.");
        }

        return result;
    }

}
