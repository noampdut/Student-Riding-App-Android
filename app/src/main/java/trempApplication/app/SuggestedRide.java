package trempApplication.app;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class SuggestedRide implements Serializable {
    UUID RideId;
    double Distance;
    double Duration;
    List<String> Instructions;
    List<PickUpPoint> pickUpPoints;
    Passenger Driver;
    String PickUpTime;
    String PickUpPoint;
    double Relevance;
    int Capacity;
    String Similarity;
}

