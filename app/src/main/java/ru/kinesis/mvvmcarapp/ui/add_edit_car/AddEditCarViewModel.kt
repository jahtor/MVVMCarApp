package ru.kinesis.mvvmcarapp.ui.add_edit_car

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.kinesis.mvvmcarapp.data.Car
import ru.kinesis.mvvmcarapp.data.Repository
import ru.kinesis.mvvmcarapp.util.UiEvent
import javax.inject.Inject

@HiltViewModel
class AddEditCarViewModel @Inject constructor(
    private val repository: Repository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var car by mutableStateOf<Car?>(null)
        private set

    var name by mutableStateOf("")
        private set

    var age by mutableStateOf("")
        private set

    var vin by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val carId = savedStateHandle.get<Int>("carId")!!
        if (carId != -1){
            viewModelScope.launch {
                repository.getCarById(carId)?.let { car ->
                    name = car.name
                    age = car.age.toString()
                    vin = car.vin
                    this@AddEditCarViewModel.car = car
                }
            }
        }
    }

    fun onEvent(event: AddEditCarEvent){
        when(event) {
            is AddEditCarEvent.OnNameChange -> {
                name = event.name
            }
            is AddEditCarEvent.OnAgeChange -> {
                age = event.age.toString()
            }
            is AddEditCarEvent.OnVinChange -> {
                vin = event.vin
            }
            is AddEditCarEvent.OnSaveCarClick -> {
                viewModelScope.launch {
                    if (name.isBlank()){ //проверка на пустую дату
                        sendUiEvent(UiEvent.ShowSnackbar(
                            message = "Name can't be empty!"
                        ))
                        return@launch
                    }
                    repository.addCar( //помещаем данные в БД
                        Car(
                            name = name,
                            age = age.toInt(),
                            vin = vin,
                            id = car?.id
                        )
                    )
                    sendUiEvent(UiEvent.PopBackStack) //переход обратно к списку
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