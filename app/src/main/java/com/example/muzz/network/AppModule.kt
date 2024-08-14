package com.example.muzz.network

import android.app.Application
import androidx.room.Room
import com.example.muzz.respository.ChatDatabase
import com.example.muzz.respository.ChatRepository
import com.example.muzz.respository.ChatRepositoryImpl
import com.example.muzz.respository.MessageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module that provides dependencies for the application.
 * This module is installed in the SingletonComponent, meaning the provided dependencies will have a singleton lifespan.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Provides a singleton instance of the ChatDatabase.
     *
     * @param app The application instance.
     * @return A singleton ChatDatabase instance.
     */
    @Provides
    @Singleton
    fun provideDatabase(app: Application): ChatDatabase {
        return Room.databaseBuilder(app, ChatDatabase::class.java, "chat_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    /**
     * Provides a singleton instance of the MessageDao.
     *
     * @param db The ChatDatabase instance.
     * @return A singleton MessageDao instance.
     */
    @Provides
    @Singleton
    fun provideMessageDao(db: ChatDatabase): MessageDao {
        return db.messageDao()
    }

    /**
     * Provides a singleton instance of the ChatRepository.
     *
     * @param messageDao The MessageDao instance.
     * @return A singleton ChatRepository instance.
     */
    @Provides
    @Singleton
    fun provideRepository(messageDao: MessageDao): ChatRepository {
        return ChatRepositoryImpl(messageDao)
    }
}


//Explanation:
//Class-Level Comment: Describes the purpose of the AppModule class, which is to provide dependencies for the application using Dagger Hilt.
//@Module Annotation: Indicates that this class is a Dagger Hilt module.
//@InstallIn Annotation: Specifies that the module will be installed in the SingletonComponent, meaning all the provided dependencies will have a singleton lifespan.
//provideDatabase Function:
//Comment: Explains that this function provides a singleton instance of ChatDatabase.
//Parameter: app is the application instance.
//Return Type: Returns a singleton ChatDatabase instance.
//Implementation Details: Uses Room.databaseBuilder to create the database, with fallbackToDestructiveMigration to handle schema migrations by destroying and recreating the database.
//provideMessageDao Function:
//Comment: Explains that this function provides a singleton instance of MessageDao.
//Parameter: db is the ChatDatabase instance.
//Return Type: Returns a singleton MessageDao instance.
//provideRepository Function:
//Comment: Explains that this function provides a singleton instance of ChatRepository.
//Parameter: messageDao is the MessageDao instance.
//Return Type: Returns a singleton ChatRepository instance.
//Implementation Details: Uses ChatRepositoryImpl to create the repository implementation.