package com.ds.assignment1_2.entity.validator;

import com.ds.assignment1_2.entity.Flight;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FlightValidator {

    private static final String NUMBER_REGEX = "[A-Z]{2}[0-9]{4}";

    private final Flight flight;
    private final List<String> errors;

    public FlightValidator(Flight flight) {
        this.flight = flight;
        errors = new ArrayList<>();
    }

    public List<String> getErrors() {
        return errors;
    }

    public boolean validate(){
        validateNumber(flight.getFlightNumber());

        return errors.isEmpty();
    }

    private void validateNumber(String flightNumber) {
        if (!Pattern.compile(NUMBER_REGEX).matcher(flightNumber).matches()) {
            errors.add("Invalid flight number!");
        }
    }


}
