package ru.kinesis.mvvmcarapp.ui.add_edit_car

sealed class AddEditCarEvent {
    data class OnNameChange(val name: String): AddEditCarEvent()
    data class OnAgeChange(val age: Int): AddEditCarEvent()
    data class OnVinChange(val vin: String): AddEditCarEvent()
    object OnSaveCarClick: AddEditCarEvent()
}
