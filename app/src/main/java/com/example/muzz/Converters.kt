package com.example.muzz

import androidx.room.TypeConverter
import java.util.Date

/**
 * Class providing type converters for Room to handle custom data types.
 */
class Converters {

    /**
     * Converts a Long timestamp to a Date object.
     *
     * @param value The Long timestamp value.
     * @return The corresponding Date object, or null if the value is null.
     */
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    /**
     * Converts a Date object to a Long timestamp.
     *
     * @param date The Date object.
     * @return The corresponding Long timestamp value, or null if the date is null.
     */
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}

//Explanation
//The Converters class is a utility class used by the Room persistence library to handle conversions between custom data types and primitive data types that Room can store in the database.
//Room uses these type converters to seamlessly convert complex data types to and from the database.
//fromTimestamp(): Converts a Long timestamp value from the database to a Date object. This is useful for retrieving Date objects from the database.
//dateToTimestamp(): Converts a Date object to a Long timestamp value. This is useful for storing Date objects in the database.
//By providing these type converters, the Converters class enables Room to store and retrieve Date objects, which are not natively supported by Room.
//This class ensures that the conversion process is handled transparently, allowing developers to work with Date objects in their data models without worrying about how they are stored in the database.