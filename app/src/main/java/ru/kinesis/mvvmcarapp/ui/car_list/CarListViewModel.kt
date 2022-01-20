package ru.kinesis.mvvmcarapp.ui.car_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.kinesis.mvvmcarapp.data.Car
import ru.kinesis.mvvmcarapp.data.Repository
import ru.kinesis.mvvmcarapp.util.Routes
import ru.kinesis.mvvmcarapp.util.UiEvent
import javax.inject.Inject

@HiltViewModel
class CarListViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    val cars = repository.getAllCars()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedCar: Car? = null

    //функция описания действий по событиям
    fun onEvent(event: CarListEvent){
        when(event){
            is CarListEvent.OnCarClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.REPAIR_LIST + "?carId=${event.car.id}"))
            }
            is CarListEvent.OnAddCarClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_CAR))
            }
            is CarListEvent.OnUndoDeleteClick -> {
                deletedCar?.let {
                    viewModelScope.launch {
                        repository.addCar(it)
                    }
                }
            }
            is CarListEvent.OnDeleteCar -> {
                //TODO при удалении машины, удалять все записи ремонта по машине
                viewModelScope.launch {
                    deletedCar = event.car
                    repository.deleteCar(event.car)
                    sendUiEvent(UiEvent.ShowSnackbar(
                        message = "Entry deleted!",
                        action = "Undo"
                    ))
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}