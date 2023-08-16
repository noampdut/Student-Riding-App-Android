package Converters;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TypeConverters {
    @TypeConverter
    public static String fromUuidList(List<UUID> uuids) {
        if (uuids == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (UUID uuid : uuids) {
            sb.append(uuid.toString()).append(",");
        }
        return sb.toString();
    }

    @TypeConverter
    public static List<UUID> toUuidList(String uuidsString) {
        if (uuidsString == null) {
            return null;
        }
        String[] uuidStrings = uuidsString.split(",");
        List<UUID> uuids = new ArrayList<>();
        for (String uuidString : uuidStrings) {
            uuids.add(UUID.fromString(uuidString));
        }
        return uuids;
    }
}

