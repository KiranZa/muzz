package com.example.muzz.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.muzz.data.Message
import com.example.muzz.respository.ChatRepository
import com.example.muzz.viewmodel.ChatViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.util.Date
import java.util.UUID

@ExperimentalCoroutinesApi
class ChatViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ChatViewModel
    private val repository = mock(ChatRepository::class.java)
    private val observer = mock<Observer<List<Message>>>()
    private val messagesLiveData = MutableLiveData<List<Message>>()

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        // Set up the repository to return the non-null LiveData object
        `when`(repository.getMessages()).thenReturn(messagesLiveData)

        // Initialize the ViewModel with the mocked repository
        viewModel = ChatViewModel(repository)
        viewModel.messages.observeForever(observer)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun sendMessage() = runBlockingTest {
        val content = "Hello"
        viewModel.sendMessage(content)
        val captor = argumentCaptor<Message>()
        verify(repository, times(1)).addMessage(captor.capture())
        assert(captor.firstValue.content == content)
    }

    @Test
    fun markMessagesAsRead() = runBlockingTest {
        val messages = listOf(
            Message(id = UUID.randomUUID().toString(), content = "Hello", isSent = false, sender = "Other", timestamp = Date(), isRead = false),
            Message(id = UUID.randomUUID().toString(), content = "Hi", isSent = false, sender = "Other", timestamp = Date(), isRead = false)
        )
        `when`(repository.getUnreadMessages("Other")).thenReturn(messages)
        viewModel.markMessagesAsRead(isUser = true)
        verify(repository, times(2)).updateMessage(any())
    }
}