package trempApplication.app;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Passenger implements Serializable {

    UUID Id;
    String IdNumber;
    String UserName;
    String Faculty;
    String PhoneNumber;
    List<UUID> CarIds;
    String Bio;
    String Token;
    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }




    public Passenger(String idNumber, String userName, String faculty, String phoneNumber) {
        this.IdNumber = idNumber;
        this.UserName = userName;
        this.Faculty = faculty;
        this.PhoneNumber = phoneNumber;
        this.Bio = "";
        this.Token = "";
    }

    public UUID getId() {
        return Id;
    }

    public String getIdNumber() {
        return IdNumber;
    }

    public String getUserName() {
        return UserName;
    }

    public String getFaculty() {
        return Faculty;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public List<UUID> getCarIds() {
        return CarIds;
    }

    public void setCarIds(List<UUID> carIds) {
        carIds = carIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return Objects.equals(IdNumber, passenger.IdNumber) &&
                Objects.equals(UserName, passenger.UserName) &&
                Objects.equals(Faculty, passenger.Faculty) &&
                Objects.equals(PhoneNumber, passenger.PhoneNumber) &&
                Objects.equals(CarIds, passenger.CarIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, IdNumber, UserName, Faculty, PhoneNumber, CarIds);
    }



    protected Passenger(Parcel parcel) {
        IdNumber = parcel.readString();
        UserName = parcel.readString();
        Faculty = parcel.readString();
        PhoneNumber = parcel.readString();
    }
}
