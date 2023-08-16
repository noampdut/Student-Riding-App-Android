package trempApplication.app;
import android.os.Build;
import androidx.annotation.RequiresApi;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Ride {

    UUID Id;
    Passenger Driver;

    UUID CarId;

    int Capacity;
    String Source;

    String Dest;

    Boolean ToUniversity;

    Date Date;

    List<PickUpPoint> pickUpPoints;

    Double Duration;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public Ride(Passenger Driver, UUID CarId, int Capacity, String Source, String Dest, String leavingTime, String day) {
        this.Driver = Driver;
        this.CarId = CarId;
        this.Capacity = Capacity;
        this.Source = Source;
        this.Dest = Dest;
        this.pickUpPoints = new ArrayList<PickUpPoint>();
        this.Date = Date.getDate(day, leavingTime);
        if (Source.contains("Bar-Ilan University")) {
            ToUniversity = false;
        } else{
            ToUniversity = true;
        }
    }
    public  Ride(){}

    public UUID getUuid() {
        return Id;
    }

    public void setUuid(UUID uuid) {
        this.Id = uuid;
    }

    public Passenger getDriver() {
        return Driver;
    }

    public void setDriverId(Passenger driverId) {
        this.Driver = driverId;
    }

    public UUID getCarId() {
        return CarId;
    }

    public void setCarId(UUID carId) {
        this.CarId = carId;
    }

    public int getCapacity() {
        return Capacity;
    }

    public void setCapacity(int capacity) {
        this.Capacity = capacity;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        this.Source = source;
    }

    public String getDest() {
        return Dest;
    }

    public void setDest(String dest) {
        this.Dest = dest;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        this.Date = date;
    }
}
