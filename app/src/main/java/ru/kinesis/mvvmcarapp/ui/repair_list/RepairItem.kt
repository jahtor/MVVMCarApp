package ru.kinesis.mvvmcarapp.ui.repair_list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.kinesis.mvvmcarapp.data.Repair

@Composable
fun RepairItem(
    repair: Repair,
    onEvent: (RepairListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
        elevation = 8.dp,
        border = BorderStroke(1.dp, Color.Black),
//        backgroundColor = Color.Gray
    ) {
        Column(
//            modifier = Modifier.padding(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.Gray)
            ) {
                Column(modifier = Modifier.weight(3f).padding(start = 4.dp)) {
                    Text(
//                        modifier = Modifier.weight(3f),
                        text = "Date:",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
//                        modifier = Modifier.weight(3f),
                        text = repair.date,
                        fontWeight = FontWeight.Bold
                    )
                }
//                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(3f)) {
                    Text(
//                        modifier = Modifier.weight(3f),
                        text = "Mileage:",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
//                        modifier = Modifier.weight(3f),
                        text = repair.mileage.toString() + "km",
                        fontWeight = FontWeight.Bold
                    )
                }
//                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(3f)) {
                    Text(
//                        modifier = Modifier.weight(3f),
                        text = "Price:",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
//                        modifier = Modifier.weight(3f),
                        text = repair.price.toString() + "$",
                        fontWeight = FontWeight.Bold
                    )
                }
//                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                    onEvent(RepairListEvent.OnDeleteRepair(repair))
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                repair.description?.let { Text(text = it) }
            }
        }
    }

/*
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
        elevation = 8.dp,
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Row() {
            Column(modifier = Modifier
                .width(120.dp)) {
                Text(
                    text = repair.date,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = repair.mileage.toString(),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = repair.price.toString(),
                    fontWeight = FontWeight.Bold
                )
            }

            repair.description?.let { Text(text = it) }

            IconButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    onEvent(RepairListEvent.OnDeleteRepair(repair))
                }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
        }
    }
*/

}