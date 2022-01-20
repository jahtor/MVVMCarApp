package ru.kinesis.mvvmcarapp.ui.repair_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.kinesis.mvvmcarapp.data.Repair
import ru.kinesis.mvvmcarapp.data.Repository
import ru.kinesis.mvvmcarapp.util.Routes
import ru.kinesis.mvvmcarapp.util.UiEvent
import javax.inject.Inject

@HiltViewModel
class RepairListViewModel @Inject constructor(
    private val repository: Repository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val carId = savedStateHandle.get<Int>("carId")!!

    val repairs = repository.getAllRepair(carId)

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedRepair: Repair? = null

    //функция описания действий по событиям
    fun onEvent(event: RepairListEvent){
        when(event){
            is RepairListEvent.OnRepairClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_REPAIR + "?repairId=${event.repair.id}"))
            }
            is RepairListEvent.OnAddRepairClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_REPAIR + "?carId=${carId}"))
            }
            is RepairListEvent.OnUndoDeleteClick -> {
                deletedRepair?.let {
                    viewModelScope.launch {
                        repository.addRepair(it)
                    }
                }
            }
            is RepairListEvent.OnDeleteRepair -> {
                viewModelScope.launch {
                    deletedRepair = event.repair
                    repository.deleteRepair(event.repair)
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