package ru.kinesis.mvvmcarapp.data

import androidx.room.*
import androidx.room.Dao
import kotlinx.coroutines.flow.Flow

// описание взаимодействия с БД

@Dao
interface Dao {

    //функция добавления машины
    @Insert(onConflict = OnConflictStrategy.REPLACE) //что делать если запись с таким id существует
    suspend fun addCar(car: Car)

    //функция удаления машины
    @Delete
    suspend fun deleteCar(car: Car)

    //функция получения машины по id
    @Query("SELECT * FROM car WHERE id = :id")
    suspend fun getCarById(id: Int): Car?

    //функция вывода всех машин
    @Query("SELECT * FROM car")
    fun getAllCars(): Flow<List<Car>>

    //функция добавления записи ремонта
    @Insert(onConflict = OnConflictStrategy.REPLACE) //что делать если запись с таким id существует
    suspend fun addRepair(repair: Repair)

    //функция удаления записи ремонта
    @Delete
    suspend fun deleteRepair(repair: Repair)

    //функция получения записи ремонта по id
    @Query("SELECT * FROM repair WHERE id = :id")
    suspend fun getRepairById(id: Int): Repair?

    //функция вывода всех записей для выбранного автомобиля (в обратном порядке)
    @Query("SELECT * FROM repair WHERE carId = :carId ORDER BY date DESC")
    fun getAllRepair(carId: Int): Flow<List<Repair>>
}