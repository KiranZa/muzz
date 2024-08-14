package com.example.muzz

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import com.example.muzz.data.Message
import com.example.muzz.respository.ChatRepositoryImpl
import com.example.muzz.respository.MessageDao
import java.util.Date

@ExperimentalCoroutinesApi
class MessageRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: ChatRepositoryImpl
    private val dao = mock(MessageDao::class.java)

    @Before
    fun setup() {
        repository = ChatRepositoryImpl(dao)
    }

    @Test
    fun addMessage() = runBlockingTest {
        val message = Message(id = "1", content = "Hello", isSent = true, sender = "User", timestamp = Date(), isRead = false)
        repository.addMessage(message)
        verify(dao).insert(message)
    }

    @Test
    fun updateMessage() = runBlockingTest {
        val message = Message(id = "1", content = "Hello", isSent = true, sender = "User", timestamp = Date(), isRead = false)
        repository.updateMessage(message)
        verify(dao).update(message)
    }

    @Test
    fun getUnreadMessages() = runBlockingTest {
        val sender = "User"
        repository.getUnreadMessages(sender)
        verify(dao).getUnreadMessages(sender)
    }
}
