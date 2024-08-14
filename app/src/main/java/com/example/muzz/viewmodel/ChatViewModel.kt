package com.example.muzz.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzz.data.Message
import com.example.muzz.respository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID
import javax.inject.Inject

/**
 * ViewModel class for managing chat messages and user interactions.
 *
 * @property repository The repository for accessing chat messages.
 */
@HiltViewModel
class ChatViewModel @Inject constructor(private val repository: ChatRepository) : ViewModel() {

    // LiveData list of messages observed by the UI
    val messages: LiveData<List<Message>> = repository.getMessages()

    // Boolean to keep track of the current user state
    var isUser: Boolean = true

    /**
     * Sends a new message with the given content.
     *
     * @param content The content of the message.
     */
    fun sendMessage(content: String) {
        viewModelScope.launch {
            val sender = if (isUser) "User" else "Other"
            val newMessage = Message(
                id = UUID.randomUUID().toString(),
                content = content,
                isSent = isUser,
                sender = sender,
                timestamp = Date()
            )
            repository.addMessage(newMessage)
        }
    }

    /**
     * Toggles the user state between "User" and "Other".
     */
    fun toggleUser() {
        isUser = !isUser
    }

    /**
     * Marks all unread messages from the opposite user as read.
     *
     * @param isUser Boolean indicating if the current user is "User".
     */
    fun markMessagesAsRead(isUser: Boolean) {
        viewModelScope.launch {
            val currentSender = if (isUser) "Other" else "User"
            val unreadMessages = repository.getUnreadMessages(currentSender)
            unreadMessages.forEach { message ->
                repository.updateMessage(message.copy(isRead = true))
            }
        }
    }
}

//Explanation
//The ChatViewModel class is responsible for managing the state and behavior of the chat feature in the application.
// It interacts with the ChatRepository to perform data operations and exposes a LiveData list of messages for the UI to observe.
//Messages LiveData: Holds the list of chat messages, observed by the UI to display messages in real-time.
//isUser Boolean: Tracks whether the current user is "User" or "Other".
//sendMessage(): Creates a new message and adds it to the repository. The message includes a unique ID, content, sender information, and a timestamp.
//toggleUser(): Switches the current user between "User" and "Other".
//markMessagesAsRead(): Marks all unread messages from the opposite user as read, ensuring that the read status is updated in the repository.
//The ChatViewModel class uses Hilt for dependency injection and viewModelScope for launching coroutines to perform asynchronous operations.
// This ensures that the ViewModel operates efficiently and integrates seamlessly with the lifecycle of the UI components.