package com.example.muzz.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.muzz.data.Message
import com.example.muzz.viewmodel.ChatViewModel
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.example.muzz.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Composable function to display the chat screen.
 *
 * @param viewModel The ViewModel for the ChatScreen.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChatScreen(viewModel: ChatViewModel = hiltViewModel()) {
    // Observing the list of messages from the ViewModel
    val messages by viewModel.messages.observeAsState(emptyList())
    // State for the content of the message input field
    var messageContent by remember { mutableStateOf(TextFieldValue("")) }
    // State to keep track of whether the current user is "User" or "Other"
    var isUser by remember { mutableStateOf(true) }
    // State of the lazy list to control scrolling
    val listState = rememberLazyListState()
    // Coroutine scope for launching coroutines
    val coroutineScope = rememberCoroutineScope()
    // Controller for the software keyboard
    val keyboardController = LocalSoftwareKeyboardController.current
    // Configuration for screen width
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    // Maximum width for message bubbles
    val maxWidthDp = with(LocalDensity.current) { screenWidthDp * 0.7f }

    // Trigger scroll to bottom when a new message is added
    LaunchedEffect(messages.size) {
        coroutineScope.launch {
            if (messages.isNotEmpty()) {
                listState.animateScrollToItem(messages.size - 1)
            }
        }
    }

    Scaffold(
        topBar = {
            // TopAppBar with a title and a toggle button to switch between "User" and "Other"
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.icon),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color.Gray, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isUser) "USER" else "OTHER",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                },
                backgroundColor = Color.White,
                contentColor = Color.Black,
                actions = {
                    Button(
                        onClick = {
                            isUser = !isUser
                            viewModel.toggleUser()
                            viewModel.markMessagesAsRead(isUser)
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF007F)),
                        shape = RoundedCornerShape(25.dp),
                        modifier = Modifier
                            .height(40.dp)
                            .padding(end = 8.dp)
                    ) {
                        Text(
                            text = if (isUser) "USER" else "OTHER",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                // LazyColumn for displaying the list of messages
                LazyColumn(
                    state = listState,
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    itemsIndexed(messages) { index, message ->
                        val showTimestamp = shouldShowTimestamp(index, messages)
                        if (showTimestamp) {
                            TimestampHeader(message.timestamp)
                        }
                        MessageCard(message, isUser, maxWidthDp)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // BasicTextField for the message input
                    BasicTextField(
                        value = messageContent,
                        onValueChange = { messageContent = it },
                        modifier = Modifier
                            .weight(1f)
                            .border(
                                width = 2.dp,
                                color = Color(0xFFFF007F),
                                shape = RoundedCornerShape(25.dp)
                            )
                            .padding(16.dp),
                        cursorBrush = SolidColor(Color.Black),
                        decorationBox = { innerTextField ->
                            if (messageContent.text.isEmpty()) {
                                Text(
                                    text = "Type a message",
                                    color = Color.Gray,
                                    modifier = Modifier.padding(start = 4.dp)
                                )
                            }
                            innerTextField()
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    // IconButton for sending the message
                    IconButton(
                        onClick = {
                            if (messageContent.text.isNotEmpty()) {
                                viewModel.sendMessage(messageContent.text)
                                messageContent = TextFieldValue("")
                                keyboardController?.hide() // Hide the keyboard
                            }
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color(0xFFFF007F), shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    )
}

/**
 * Composable function to display a message card.
 *
 * @param message The message object.
 * @param isCurrentUser Boolean indicating if the current user is "User".
 * @param maxWidth Maximum width for the message bubble.
 */
@Composable
fun MessageCard(message: Message, isCurrentUser: Boolean, maxWidth: Dp) {
    val isSentByCurrentUser = (message.sender == "User" && isCurrentUser) || (message.sender == "Other" && !isCurrentUser)
    val alignment = if (isSentByCurrentUser) Alignment.End else Alignment.Start
    val backgroundColor = if (isSentByCurrentUser) Color(0xFFFF007F) else Color(0xFFE0E0E0)
    val textColor = if (isSentByCurrentUser) Color.White else Color.Black
    val tickColor = if (message.isRead) Color.Yellow else Color.Gray

    val bubbleShape = RoundedCornerShape(
        topStart = if (isSentByCurrentUser) 16.dp else 0.dp,
        topEnd = if (isSentByCurrentUser) 0.dp else 16.dp,
        bottomStart = 16.dp,
        bottomEnd = 16.dp
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = if (isSentByCurrentUser) Arrangement.End else Arrangement.Start
    ) {
        Card(
            backgroundColor = backgroundColor,
            shape = bubbleShape,
            modifier = Modifier
                .padding(8.dp)
                .widthIn(max = maxWidth) // Limit width to 70% of screen width
        ) {
            Box(
                modifier = Modifier.wrapContentWidth()
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = message.content,
                        color = textColor,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.height(4.dp)) // Add vertical space between text and tick
                    if (isSentByCurrentUser) {
                        Text(
                            text = "\u2713\u2713", // Double tick mark
                            color = tickColor,
                            fontSize = 12.sp, // Smaller font size for the tick
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Composable function to display a timestamp header.
 *
 * @param timestamp The timestamp of the message.
 */
@Composable
fun TimestampHeader(timestamp: Date) {
    val dateFormat = SimpleDateFormat("EEEE HH:mm", Locale.getDefault())
    val formattedDate = dateFormat.format(timestamp)

    Text(
        text = formattedDate,
        style = MaterialTheme.typography.caption,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        color = Color.Gray,
        textAlign = TextAlign.Center
    )
}

/**
 * Function to determine if a timestamp should be shown before a message.
 *
 * @param index The index of the current message in the list.
 * @param messages The list of messages.
 * @return Boolean indicating if the timestamp should be shown.
 */
fun shouldShowTimestamp(index: Int, messages: List<Message>): Boolean {
    if (index == 0) return true

    val currentMessage = messages[index]
    val previousMessage = messages[index - 1]
    val diff = currentMessage.timestamp.time - previousMessage.timestamp.time
    return TimeUnit.MILLISECONDS.toHours(diff) >= 1
}



/**
 * The ChatScreen composable function creates a chat interface that includes a top bar,
 * a list of messages, and an input field for composing new messages.
 * The user can toggle between two user states ("User" and "Other"),
 * and the interface supports scrolling to the bottom when new messages are added.
 * Message cards are displayed with appropriate styling based on the sender,
 * and timestamps are shown when there is more than an hour gap between messages.
 */
