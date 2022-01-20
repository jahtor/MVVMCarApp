package ru.kinesis.mvvmcarapp.ui.add_edit_repair

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
import ru.kinesis.mvvmcarapp.data.Repair
import ru.kinesis.mvvmcarapp.data.Repository
import ru.kinesis.mvvmcarapp.util.UiEvent
import javax.inject.Inject

@HiltViewModel
class AddEditRepairViewModel @Inject constructor(
    private val repository: Repository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var repair by mutableStateOf<Repair?>(null)
        private set

    var carId = savedStateHandle.get<Int>("carId")

    var date by mutableStateOf("")
        private set

    var mileage by mutableStateOf("")
        private set

    var price by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val repairId = savedStateHandle.get<Int>("repairId")!!
        if (repairId != -1){
            viewModelScope.launch {
                repository.getRepairById(repairId)?.let { repair ->
                    carId = repair.carId
                    date = repair.date
                    mileage = repair.mileage.toString()
                    price = repair.price.toString()
                    description = repair.description ?: ""
                    this@AddEditRepairViewModel.repair = repair
                }

            }
        }
    }

    fun onEvent(event: AddEditRepairEvent){
        when(event) {
            is AddEditRepairEvent.OnDateChange -> {
                date = event.date
            }
            is AddEditRepairEvent.OnMileageChange -> {
                mileage = event.mileage.toString()
            }
            is AddEditRepairEvent.OnPriceChange -> {
                price = event.price.toString()
            }
            is AddEditRepairEvent.OnDescriptionChange -> {
                description = event.description
            }
            is AddEditRepairEvent.OnSaveRepairClick -> {
                viewModelScope.launch {
                    if (date.isBlank()){ //проверка на пустую дату
                        sendUiEvent(UiEvent.ShowSnackbar(
                            message = "Date can't be empty!"
                        ))
                        return@launch
                    }
                    repository.addRepair( //помещаем данные в БД
                        Repair(
                            carId = carId!!.toInt(),
                            date = date,
                            mileage = mileage.toInt(),
                            price = price.toInt(),
                            description = description,
                            id = repair?.id
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