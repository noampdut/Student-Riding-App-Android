package Converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class StringListConverter {
    @TypeConverter
    public String fromList(List<String> list) {
        if (list == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        return gson.toJson(list, type);
    }

    @TypeConverter
    public List<String> toList(String string) {
        if (string == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(string, type);
    }
}
