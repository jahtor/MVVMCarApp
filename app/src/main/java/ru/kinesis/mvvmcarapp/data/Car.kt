package ru.kinesis.mvvmcarapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//создаем таблицу БД для списка машин

@Entity
data class Car(
    @PrimaryKey val id: Int? = null,
    val name: String,
    val age: Int,
    val vin: String
)
