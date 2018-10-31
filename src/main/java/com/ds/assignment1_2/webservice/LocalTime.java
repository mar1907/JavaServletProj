package com.ds.assignment1_2.webservice;

import com.ds.assignment1_2.entity.City;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class LocalTime {

    private static final String PATH = "http://new.earthtools.org/timezone/%s/%s";

    public static Date getTimeIn(City city) throws Exception{
        String path = String.format(PATH, city.getLatitude(), city.getLongitude());

        URL url = new URL(path);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        InputStream xml = connection.getInputStream();

        SAXBuilder sb = new SAXBuilder();
        Document doc = sb.build(xml);

        Element el = doc.getRootElement().getChild("localtime");

        Date date = new Date(el.getText());

        connection.disconnect();

        return date;
    }

}
