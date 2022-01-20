package ru.kinesis.mvvmcarapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

//описание БД машин

@Database(
    entities = [Car::class, Repair::class], //список таблиц в базе
    version = 1
)
abstract class Database: RoomDatabase() {

    abstract val dao: Dao
}