package com.example.muzz.respository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.muzz.data.Message
import com.example.muzz.Converters
import androidx.room.TypeConverters

/**
 * The Room database class for the chat application.
 * This class defines the database configuration and serves as the main access point
 * for the underlying connection to the app's persisted data.
 */
@Database(entities = [Message::class], version = 2)
@TypeConverters(Converters::class)
abstract class ChatDatabase : RoomDatabase() {
    /**
     * Abstract method to get the MessageDao.
     * Room will generate the implementation of this method when the database is built.
     *
     * @return The MessageDao instance.
     */
    abstract fun messageDao(): MessageDao
}


//Explanation:
//Class-Level Comment: Describes the purpose of the ChatDatabase class, which is the Room database class for the chat application.
// It explains that this class defines the database configuration and serves as the main access point for the underlying connection to the app's persisted data.
//@Database Annotation: Specifies the list of entities associated with the database (Message::class) and the version of the database (version = 2).
//@TypeConverters Annotation: Specifies the list of TypeConverters associated with the database (Converters::class).
// These converters handle the conversion of custom types (e.g., Date) to and from types that the database can store.
//messageDao Method Comment: Describes the abstract method messageDao() that provides access to the MessageDao.
// Room will generate the implementation of this method when the database is built.
// The method returns an instance of MessageDao, which contains the methods used to access the data in the Message table.




