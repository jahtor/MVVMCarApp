package ru.kinesis.mvvmcarapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//создаем таблицу БД для списка работ

@Entity
data class Repair(
    val date: String,
    val mileage: Int,
    val description: String?,
    val price: Int?,
    val carId: Int,
    @PrimaryKey val id: Int? = null
)
