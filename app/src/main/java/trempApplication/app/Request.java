package trempApplication.app;

import static trempApplication.app.Date.getDate;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class Request {
    String origin;
    String destination;
    List<String> waypoints;
    Date date;
    Boolean toUniversity;
    String PassengerId;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Request(String to, String from, String pickup_time, String day, String passengerId) {
        this.origin = from;
        this.destination = to;
        this.waypoints = new ArrayList<>();
        this.date = getDate(day, pickup_time);
        this.PassengerId = passengerId;
        if (from.contains("Bar-Ilan University")){
            toUniversity = false;
        } else{
            toUniversity = true;
        }
    }
}
