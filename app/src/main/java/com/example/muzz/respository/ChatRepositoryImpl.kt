package com.example.muzz.respository

import androidx.lifecycle.LiveData
import com.example.muzz.data.Message
import javax.inject.Inject

/**
 * Implementation of the ChatRepository interface.
 * This class provides concrete implementations for the methods defined in the ChatRepository interface,
 * interacting with the MessageDao to perform database operations.
 *
 * @param messageDao The DAO used to interact with the messages database.
 */
class ChatRepositoryImpl @Inject constructor(private val messageDao: MessageDao) : ChatRepository {

    /**
     * Retrieves all messages from the database, ordered by timestamp in ascending order.
     *
     * @return A LiveData list of all messages.
     */
    override fun getMessages(): LiveData<List<Message>> = messageDao.getMessages()

    /**
     * Adds a new message to the database.
     *
     * @param message The message to be added.
     */
    override suspend fun addMessage(message: Message) {
        messageDao.insert(message)
    }

    /**
     * Updates an existing message in the database.
     *
     * @param message The message to be updated.
     */
    override suspend fun updateMessage(message: Message) {
        messageDao.update(message)
    }

    /**
     * Retrieves all unread messages from the database for a specific sender.
     *
     * @param sender The sender whose unread messages are to be retrieved.
     * @return A list of unread messages from the specified sender.
     */
    override suspend fun getUnreadMessages(sender: String): List<Message> {
        return messageDao.getUnreadMessages(sender)
    }
}

//Explanation:
//Class-Level Comment: Describes the purpose of the ChatRepositoryImpl class, which is to provide concrete implementations for the methods defined in the ChatRepository interface. It interacts with the MessageDao to perform database operations.
//Constructor Comment: Explains that the constructor takes a MessageDao as a parameter, which is used to interact with the messages database. The @Inject annotation indicates that Hilt will provide the MessageDao instance when creating ChatRepositoryImpl.
//getMessages Method Comment: Explains that this method retrieves all messages from the database, ordered by timestamp in ascending order, and returns a LiveData list of these messages.
//addMessage Method Comment: Describes that this method adds a new message to the database. The message parameter is the message to be added.
//updateMessage Method Comment: Explains that this method updates an existing message in the database. The message parameter is the message to be updated.
//getUnreadMessages Method Comment: Describes that this method retrieves all unread messages from the database for a specific sender. The sender parameter specifies the sender whose unread messages are to be retrieved, and the method returns a list of these unread messages.