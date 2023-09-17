package com.example.hicardipresscenter.global;

public class DateUtil {

    // yy. MM. dd. AM hh:mm -> 20yy.MM.dd
    public static String dateFormatter(String date) {
        String[] splitDate = date.split(" ");

        String year = splitDate[0];
        String month = splitDate[1];
        String day = splitDate[2];

        if (month.length() == 2) {
            month = "0" + month;
        }

        if (day.length() == 2) {
            day = "0" + day;
        }

        day = day.substring(0, 2);

        return "20" + year + month + day;
    }

    // 9/16/23, 11:49 PM -> 20yy.MM.dd
    public static String newsDateFormatter(String date) {

        String[] splitDate = date.split("/");

        String month = splitDate[0];
        String day = splitDate[1];
        String year = splitDate[2].substring(0, 2);

        if (month.length() == 1) {
            month = "0" + month;
        }

        if (day.length() == 1) {
            day = "0" + day;
        }

        return "20" + year + "." + month + "." + day;
    }
}
