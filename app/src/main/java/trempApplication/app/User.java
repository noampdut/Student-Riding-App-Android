package trempApplication.app;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;
@Entity
public class User {
    @PrimaryKey
    @NonNull
    String IdNumber;
    @ColumnInfo(name = "Password")
    String Password;
    @ColumnInfo(name = "Token")
    String Token;


    public User(String idNumber, String password, String token) {
        IdNumber = idNumber;
        Password = password;
        Token = token;
    }
    public User(){};

    public String getIdNumber() {
        return IdNumber;
    }

    public String getPassword() {
        return Password;
    }
}
