package ru.kinesis.mvvmcarapp.data

import kotlinx.coroutines.flow.Flow

//описание репозитория

class RepositoryImpl(
    private val dao: Dao
): Repository {

    //функция добавления записи
    override suspend fun addCar(car: Car) {
        dao.addCar(car)
    }

    //функция удаления записи
    override suspend fun deleteCar(car: Car) {
        dao.deleteCar(car)
    }

    //функция получения записи по id
    override suspend fun getCarById(id: Int): Car? {
        return dao.getCarById(id)
    }

    //функция вывода всех записей
    override fun getAllCars(): Flow<List<Car>> {
        return dao.getAllCars()
    }

    //функция добавления записи
    override suspend fun addRepair(repair: Repair) {
        dao.addRepair(repair)
    }

    //функция удаления записи
    override suspend fun deleteRepair(repair: Repair) {
        dao.deleteRepair(repair)
    }

    //функция получения записи по id
    override suspend fun getRepairById(id: Int): Repair? {
        return dao.getRepairById(id)
    }

    //функция вывода всех записей для выбранного автомобиля
    override fun getAllRepair(carId: Int): Flow<List<Repair>> {
        return dao.getAllRepair(carId)
    }
}