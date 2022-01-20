package ru.kinesis.mvvmcarapp.ui.car_list

import ru.kinesis.mvvmcarapp.data.Car

// события которые могут быть вызваны из UI экрана списка машин

sealed class CarListEvent{
    //удаление
    data class OnDeleteCar(val car: Car): CarListEvent()

    // Undo delete
    object OnUndoDeleteClick: CarListEvent()

    //открытие истории работ по авто
    data class OnCarClick(val car: Car): CarListEvent()

    //добавление
    object OnAddCarClick: CarListEvent()
}
