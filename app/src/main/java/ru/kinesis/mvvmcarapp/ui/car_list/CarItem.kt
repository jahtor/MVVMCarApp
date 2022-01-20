package ru.kinesis.mvvmcarapp.ui.car_list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.kinesis.mvvmcarapp.data.Car

@Composable
fun CarItem(
    car: Car,
    onEvent: (CarListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
        elevation = 8.dp,
        border = BorderStroke(1.dp, Color.Black),
    ) {
       Row(
           modifier = Modifier.fillMaxWidth(),
           verticalAlignment = Alignment.CenterVertically
       ) {
           Box(
               modifier = Modifier
                   .width(50.dp)
                   .background(Color.Gray)
                   .fillMaxSize()
           ) {
               Icon(
                   imageVector = Icons.Default.AccountCircle,
                   contentDescription = "Car",
                   modifier = Modifier.align(Alignment.Center)
               )
           }
           Column() {
               Text(
                   text = "Name: " + car.name + " (" + car.age + ")",
                   fontWeight = FontWeight.Bold
               )
               Text(
                   text = "VIN: " + car.vin,
                   fontWeight = FontWeight.Bold
               )
           }
           //TODO меню: редактировать/удалить автомобиль
/*
           IconButton(
               modifier = Modifier.width(20.dp),
               onClick = {
                   onEvent(CarListEvent.OnDeleteCar(car))
               }) {
               Icon(
                   imageVector = Icons.Default.Delete,
                   contentDescription = "Delete"
               )
           }
*/
       }
    }
}