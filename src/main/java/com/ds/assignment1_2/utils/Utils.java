package com.ds.assignment1_2.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String getTimeFromDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }
}
