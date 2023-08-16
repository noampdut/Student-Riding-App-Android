package trempApplication.app;
import android.os.Build;
import androidx.annotation.RequiresApi;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class Date {
    String Year;
    String Month;
    String Day;
    String Hour;
    String Minute;


    public Date(String year, String month, String day, String hour, String minute) {
        this.Year = year;
        this.Month = month;
        this.Day = day;
        this.Hour = hour;
        this.Minute = minute;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Date getDate(String day, String time) {
        String[] parts = time.split(":");


        TimeZone israelTimeZone = TimeZone.getTimeZone("Europe/Athens");
        ZoneId israelZoneId = israelTimeZone.toZoneId();
        ZonedDateTime now = ZonedDateTime.now(israelZoneId);
        LocalDateTime today = now.toLocalDateTime();

        //LocalDateTime today = timeNow.plusDays(1).plusHours(13);

        LocalDateTime tomorrow = today.plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        if (day.equals("Today")) {
            String formattedDate = today.format(formatter);
            String[] parts2 = formattedDate.split("-");
            Date date = new Date(parts2[2], parts2[1], parts2[0], parts[0], parts[1]);
            return date;
        } else {
            String formattedDate = tomorrow.format(formatter);
            String[] parts2 = formattedDate.split("-");
            Date date = new Date(parts2[2], parts2[1], parts2[0], parts[0], parts[1]);
            return date;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean itsTooLate(String day, String time) {
        TimeZone israelTimeZone = TimeZone.getTimeZone("Europe/Athens");
        ZoneId israelZoneId = israelTimeZone.toZoneId();
        ZonedDateTime now = ZonedDateTime.now(israelZoneId);
        LocalDateTime currentTime = now.toLocalDateTime();

        LocalDateTime specificDateTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDateTime today = now.toLocalDateTime();
        LocalDateTime tomorrow = today.plusDays(1);
        String formattedDate;
        if (day.equals("Today")) {
            formattedDate = today.format(formatter);
        } else {
            formattedDate = tomorrow.format(formatter);
        }
        // Parse the specific date and time
        String specificDateTimeString = formattedDate + " " + time;
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        specificDateTime = LocalDateTime.parse(specificDateTimeString, formatter2);

        // Compare the current date and time with the specific date and time
        return currentTime.isAfter(specificDateTime);
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        this.Year = year;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        this.Month = month;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        this.Day = day;
    }

    public String getHour() {
        return Hour;
    }

    public void setHour(String hour) {
        this.Hour = hour;
    }

    public String getMinute() {
        return Minute;
    }

    public void setMinute(String minute) {
        this.Minute = minute;
    }

    public String getDate(){
        StringBuilder sb = new StringBuilder("");
        sb.append(Day);
        sb.append(".");
        sb.append(Month);
        sb.append(".");
        sb.append(Year);
        return sb.toString();
    }
    public String getTime() {
        StringBuilder sb = new StringBuilder("");
        sb.append(Hour);
        sb.append(":");
        sb.append(Minute);
        return sb.toString();
    }
}
