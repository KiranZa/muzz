package com.example.muzz.network

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Custom Application class for the chat application.
 * This class is annotated with @HiltAndroidApp to trigger Hilt's code generation,
 * including a base class for the application that uses Hilt for dependency injection.
 */
@HiltAndroidApp
open class ChatApplication : Application() {

    /**
     * Called when the application is starting, before any other application objects have been created.
     * This is where you should initialize components that need to be set up before the application is used.
     */
    override fun onCreate() {
        super.onCreate()
        // Initialize any necessary components here
    }
}

//Explanation:
//Class-Level Comment: Describes the purpose of the ChatApplication class,
//which is the custom application class for the chat application.
//It explains the use of the @HiltAndroidApp annotation.
//@HiltAndroidApp Annotation: Indicates that Hilt should be used for dependency injection in this application.
//This annotation triggers Hilt's code generation, including generating a base class for the application.
//onCreate Method Comment: Explains that the onCreate method is called when the application is starting,
//before any other application objects have been created. It is used to initialize any necessary components before the application is used.
//Empty Initialization Block: Indicates that any necessary initialization code should be placed in the onCreate method.
//Currently, the method is empty, but it provides a place for future initialization code if needed.