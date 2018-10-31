package com.ds.assignment1_2.bll;

import com.ds.assignment1_2.dao.CityDAO;
import com.ds.assignment1_2.entity.City;

import java.util.List;

public class CityBLLImplementation implements CityBLL {

    private CityDAO cityDAO;

    public CityBLLImplementation(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }

    @Override
    public List<City> findAllCities() {
        return cityDAO.findAllCities();
    }
}
