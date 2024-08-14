package com.example.muzz.respository

import androidx.lifecycle.LiveData
import com.example.muzz.data.Message

/**
 * Repository interface for managing chat messages.
 * This interface defines the methods for accessing and manipulating message data in the database.
 */
interface ChatRepository {

    /**
     * Retrieves all messages from the database, ordered by timestamp in ascending order.
     *
     * @return A LiveData list of all messages.
     */
    fun getMessages(): LiveData<List<Message>>

    /**
     * Adds a new message to the database.
     *
     * @param message The message to be added.
     */
    suspend fun addMessage(message: Message)

    /**
     * Updates an existing message in the database.
     *
     * @param message The message to be updated.
     */
    suspend fun updateMessage(message: Message)

    /**
     * Retrieves all unread messages from the database for a specific sender.
     *
     * @param sender The sender whose unread messages are to be retrieved.
     * @return A list of unread messages from the specified sender.
     */
    suspend fun getUnreadMessages(sender: String): List<Message>
}

//Explanation:
//Class-Level Comment: Describes the purpose of the ChatRepository interface, which is to manage chat messages.
//It defines methods for accessing and manipulating message data in the database.
//getMessages Method Comment: Explains that this method retrieves all messages from the database, ordered by timestamp in ascending order, and returns a LiveData list of these messages.
//addMessage Method Comment: Describes that this method adds a new message to the database. The message parameter is the message to be added.
//updateMessage Method Comment: Explains that this method updates an existing message in the database. The message parameter is the message to be updated.
//getUnreadMessages Method Comment: Describes that this method retrieves all unread messages from the database for a specific sender. The sender parameter specifies the sender whose unread
//messages are to be retrieved, and the method returns a list of these unread messages.