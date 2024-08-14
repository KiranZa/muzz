package com.example.muzz

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.muzz.respository.ChatDatabase
import com.example.muzz.respository.MessageDao
import com.example.muzz.data.Message
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date
import com.google.common.truth.Truth.assertThat

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class MessageDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ChatDatabase
    private lateinit var messageDao: MessageDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ChatDatabase::class.java
        ).allowMainThreadQueries().build()
        messageDao = database.messageDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertMessage() = runBlockingTest {
        val message = Message(id = "1", content = "Hello", isSent = true, sender = "User", timestamp = Date(), isRead = false)
        messageDao.insert(message)

        val allMessages = messageDao.getMessages().getOrAwaitValue()
        assertThat(allMessages).contains(message)
    }

    @Test
    fun updateMessage() = runBlockingTest {
        val message = Message(id = "1", content = "Hello", isSent = true, sender = "User", timestamp = Date(), isRead = false)
        messageDao.insert(message)
        val updatedMessage = message.copy(content = "Hi")
        messageDao.update(updatedMessage)

        val allMessages = messageDao.getMessages().getOrAwaitValue()
        assertThat(allMessages).contains(updatedMessage)
    }

    @Test
    fun getUnreadMessages() = runBlockingTest {
        val message1 = Message(id = "1", content = "Hello", isSent = true, sender = "User", timestamp = Date(), isRead = false)
        val message2 = Message(id = "2", content = "Hi", isSent = true, sender = "User", timestamp = Date(), isRead = true)
        messageDao.insert(message1)
        messageDao.insert(message2)

        val unreadMessages = messageDao.getUnreadMessages("User")
        assertThat(unreadMessages).containsExactly(message1)
        assertThat(unreadMessages).doesNotContain(message2)
    }
}