package trempApplication.app;

import android.view.View;

import java.io.Serializable;

public class CarAndPassenger implements Serializable {
    private Car car;
    private Passenger passenger;


    public CarAndPassenger(Car car, Passenger passenger) {
        this.car = car;
        this.passenger = passenger;
    }

    public CarAndPassenger(Car car) {
        this.car = car;
        this.passenger = null;
    }

    public CarAndPassenger(Passenger passenger) {
        this.passenger = passenger;
        this.car = null;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
}
