package trempApplication.app;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.UUID;
@Entity(tableName = "cars")
public class Car implements Serializable {
    @PrimaryKey
    @NonNull
    private UUID Id;
    @ColumnInfo(name = "CarNumber")
    private String CarNumber;
    @ColumnInfo(name = "NickName")
    private String NickName;
    @ColumnInfo(name = "Type")
    private String Type;
    @ColumnInfo(name = "Color")
    private String Color;
    @ColumnInfo(name = "Owner")
    private UUID Owner;


    public Car(String CarNumber, String NickName, String Type, String Color, UUID Owner) {
        this.CarNumber = CarNumber;
        this.NickName = NickName;
        this.Type = Type;
        this.Color = Color;
        this.Owner = Owner;
    }


    public String getCarNumber() {
        return CarNumber;
    }

    public void setCarNumber(String carNumber) {
        this.CarNumber = carNumber;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        this.NickName = nickName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        this.Color = color;
    }

    public UUID getOwner() {
        return Owner;
    }

    @NonNull
    public UUID getId() {
        return Id;
    }

    public void setId(@NonNull UUID id) {
        this.Id = id;
    }

    public void setOwner(UUID owner) {
        this.Owner = owner;
    }
}
