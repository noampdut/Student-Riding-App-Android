package trempApplication.app;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.UUID;

@Dao
public interface CarsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Car car);

    @Update
    void update(Car car);

    @Delete
    void delete(Car car);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Car> cars);

    @Query("SELECT * FROM cars WHERE Owner = :userID")
    List<Car> getCarsByUserId(UUID userID);

    @Query("SELECT * FROM cars WHERE Id = :id")
    Car getCarById(UUID id);

    @Query("SELECT * FROM cars WHERE Owner = :userID AND NickName = :carName")
    Car getCarByNameAndUserId(UUID userID, String carName);

    @Query("DELETE FROM cars")
    public void clearAllTables();



}
