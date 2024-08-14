package com.example.muzz.respository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.muzz.data.Message

/**
 * Data Access Object (DAO) for the Message entity.
 * This interface defines the methods for interacting with the messages table in the database.
 */
@Dao
interface MessageDao {

    /**
     * Retrieves all messages from the database, ordered by timestamp in ascending order.
     *
     * @return A LiveData list of all messages.
     */
    @Query("SELECT * FROM message ORDER BY timestamp ASC")
    fun getMessages(): LiveData<List<Message>>

    /**
     * Inserts a new message into the database.
     * If a message with the same primary key already exists, it will be replaced.
     *
     * @param message The message to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: Message)

    /**
     * Updates an existing message in the database.
     *
     * @param message The message to be updated.
     */
    @Update
    suspend fun update(message: Message)

    /**
     * Retrieves all unread messages from the database for a specific sender.
     *
     * @param sender The sender whose unread messages are to be retrieved.
     * @return A list of unread messages from the specified sender.
     */
    @Query("SELECT * FROM message WHERE sender = :sender AND isRead = 0")
    suspend fun getUnreadMessages(sender: String): List<Message>
}


//Explanation:
//Class-Level Comment: Describes the purpose of the MessageDao interface, which is to define methods for interacting with the messages table in the database.
//getMessages Method Comment: Explains that this method retrieves all messages from the database, ordered by timestamp in ascending order, and returns a LiveData list of these messages.
//insert Method Comment: Describes that this method inserts a new message into the database. If a message with the same primary key already exists, it will be replaced. The message parameter is the message to be inserted.
//update Method Comment: Explains that this method updates an existing message in the database. The message parameter is the message to be updated.
//getUnreadMessages Method Comment: Describes that this method retrieves all unread messages from the database for a specific sender. The sender parameter specifies the sender whose unread messages are to be retrieved, and the method returns a list of these unread messages.