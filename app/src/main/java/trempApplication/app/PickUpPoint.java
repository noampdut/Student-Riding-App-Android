package trempApplication.app;

import java.io.Serializable;

public class PickUpPoint implements Serializable {
    String PassengerId;
    String Address;
    String Time;

    public PickUpPoint(String passengerId, String address, String time) {
        this.PassengerId = passengerId;
        this.Address = address;
        this.Time = time;
    }

    public String getPassenger() {
        return PassengerId;
    }

    public void setPassenger(String passenger) {
        this.PassengerId = passenger;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        this.Time = time;
    }
}
