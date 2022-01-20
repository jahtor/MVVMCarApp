package ru.kinesis.mvvmcarapp.ui.repair_list

import ru.kinesis.mvvmcarapp.data.Car
import ru.kinesis.mvvmcarapp.data.Repair

// события которые могут быть вызваны из UI экрана списка работ

sealed class RepairListEvent{
    //удаление
    data class OnDeleteRepair(val repair: Repair): RepairListEvent()

    // Undo delete
    object OnUndoDeleteClick: RepairListEvent()

    //открытие подробностей по клику
    data class OnRepairClick(val repair: Repair): RepairListEvent()

    //добавление
//    object OnAddRepairClick: RepairListEvent()
    data class OnAddRepairClick(val carId: Int): RepairListEvent()
}
