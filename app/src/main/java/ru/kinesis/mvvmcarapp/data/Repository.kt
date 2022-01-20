package ru.kinesis.mvvmcarapp.data

import kotlinx.coroutines.flow.Flow

//описание интерфейса репозитория

interface Repository {

    //функция добавления записи машин
    suspend fun addCar(car: Car)

    //функция удаления записи машин
    suspend fun deleteCar(car: Car)

    //функция получения записи по id машины
    suspend fun getCarById(id: Int): Car?

    //функция вывода всех записей машин
    fun getAllCars(): Flow<List<Car>>

    //функция добавления записи ремонта
    suspend fun addRepair(repair: Repair)

    //функция удаления записи ремонта
    suspend fun deleteRepair(repair: Repair)

    //функция получения записи по id ремонта
    suspend fun getRepairById(id: Int): Repair?

    //функция вывода всех записей ремонта для выбранного авто
    fun getAllRepair(carId: Int): Flow<List<Repair>>
}