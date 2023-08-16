package trempApplication.app;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import Converters.DateConverter;
import Converters.StringListConverter;

@TypeConverters({StringListConverter.class, DateConverter.class, Converters.TypeConverters.class})
@Database(entities = {Car.class}, version = 19)
public abstract class MyAppDatabase extends RoomDatabase {
    public abstract CarsDao carsDao();

    private static volatile MyAppDatabase INSTANCE;

    public static MyAppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MyAppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    MyAppDatabase.class, "cars")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
