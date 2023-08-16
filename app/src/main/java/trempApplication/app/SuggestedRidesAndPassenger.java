package trempApplication.app;

import java.io.Serializable;
import java.util.List;

public class SuggestedRidesAndPassenger implements Serializable {
    List<SuggestedRide> suggestedRides;
    Passenger passenger;
    String source;
    String dest;
    String date;
    String time;

    public SuggestedRidesAndPassenger(Passenger activeUser, List<SuggestedRide> suggestedRides, String source, String dest, String date, String time) {
        this.passenger = activeUser;
        this.suggestedRides = suggestedRides;
        this.source = source;
        this. dest = dest;
        this.date = date;
        this.time = time;
    }
}
