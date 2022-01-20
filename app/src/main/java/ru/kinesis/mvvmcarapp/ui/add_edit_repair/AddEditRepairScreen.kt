package ru.kinesis.mvvmcarapp.ui.add_edit_repair

import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collect
import ru.kinesis.mvvmcarapp.util.UiEvent

@Composable
fun AddEditRepairScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditRepairViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
                else -> Unit
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditRepairEvent.OnSaveRepairClick)
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save"
                )
            }
        },
        topBar = {
            TopAppBar(
                title = {Text("CAR ID: ${viewModel.carId}")},
                backgroundColor = MaterialTheme.colors.surface
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            //TODO date picker для события

//            AndroidView(
//                { CalendarView(it) },
//                modifier = Modifier.wrapContentWidth(),
//                update = { views ->
//                    views.setOnDateChangeListener { calendarView, i, i2, i3 ->
//                    }
//                }
//            )

            TextField(
                value = viewModel.date,
                onValueChange = { viewModel.onEvent(AddEditRepairEvent.OnDateChange(it)) },
                placeholder = {
                    Text(text = "Date")
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = viewModel.mileage,
                onValueChange = { viewModel.onEvent(AddEditRepairEvent.OnMileageChange(it.toInt())) },
                placeholder = {
                    Text(text = "Mileage")
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = viewModel.price,
                onValueChange = { viewModel.onEvent(AddEditRepairEvent.OnPriceChange(it.toInt())) },
                placeholder = {
                    Text(text = "Price")
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = viewModel.description,
                onValueChange = { viewModel.onEvent(AddEditRepairEvent.OnDescriptionChange(it)) },
                placeholder = {
                    Text(text = "Description")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false
            )
        }
    }
}
