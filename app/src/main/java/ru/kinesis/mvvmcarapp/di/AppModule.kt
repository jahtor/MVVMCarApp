package ru.kinesis.mvvmcarapp.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kinesis.mvvmcarapp.data.*
import javax.inject.Singleton

//определяем функции как зависимости должны создаваться

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): Database{
        return Room.databaseBuilder(
            app,
            Database::class.java,
            "repair_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(db: Database): Repository{
        return RepositoryImpl(db.dao)
    }
}