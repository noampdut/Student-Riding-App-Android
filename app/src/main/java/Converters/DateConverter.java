package Converters;

import androidx.room.TypeConverter;

import trempApplication.app.Date;

public class DateConverter {
    @TypeConverter
    public static Date toDate(String dateString) {
        String[] dateParts = dateString.split("-");
        String year = dateParts[0];
        String month = dateParts[1];
        String day = dateParts[2];
        String hour = dateParts[3];
        String minute = dateParts[4];
        return new Date(year, month, day,hour, minute);
    }

    @TypeConverter
    public static String fromDate(Date date) {
        return date.getYear() + "-" + date.getMonth() + "-" + date.getDay();
    }


}
