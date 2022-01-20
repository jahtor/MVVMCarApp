package ru.kinesis.mvvmcarapp.ui.add_edit_repair

sealed class AddEditRepairEvent {
    data class OnDateChange(val date: String): AddEditRepairEvent()
    data class OnMileageChange(val mileage: Int): AddEditRepairEvent()
    data class OnPriceChange(val price: Int): AddEditRepairEvent()
    data class OnDescriptionChange(val description: String): AddEditRepairEvent()
    object OnSaveRepairClick: AddEditRepairEvent()
}
