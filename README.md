# Chat Application

This is a simple chat application built using Jetpack Compose, MVVM architecture, Room for local storage, and Hilt for dependency injection.

## Features

- Real-time message display using LiveData
- Persistent local storage of messages
- User toggle to simulate conversation between two users
- Timestamp display for messages
- Customizable UI with Jetpack Compose

## Assumptions Made in Implementation

### Single Chat Session

The implementation assumes a single chat session between two users. There's no support for multiple chat sessions or group chats.

### Local Storage Only

The messages are stored locally using Room Database without synchronization with a remote server. This means messages are not accessible across devices.

### Simple User Toggle

The user toggle is a simple button that switches between "User" and "Other" without actual user authentication or identification.

### Timestamp Display

Timestamps are displayed for messages that are over an hour apart from the previous message to avoid clutter.

### Default Icon and Colors

The application uses a default icon for the profile picture and predefined colors for sent and received messages without customization options.

## Architecture

The application follows the MVVM (Model-View-ViewModel) architecture pattern, which helps in separating the UI code from the business logic and data handling code.

## Dependencies

- Jetpack Compose
- Room
- Hilt
- LiveData
- Coroutine
