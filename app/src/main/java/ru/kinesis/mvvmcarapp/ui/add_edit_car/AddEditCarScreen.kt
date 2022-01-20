package ru.kinesis.mvvmcarapp.ui.add_edit_car

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collect
import ru.kinesis.mvvmcarapp.util.UiEvent

@Composable
fun AddEditCarScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditCarViewModel = hiltViewModel()
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
                viewModel.onEvent(AddEditCarEvent.OnSaveCarClick)
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save"
                )
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TextField(
                value = viewModel.name,
                onValueChange = { viewModel.onEvent(AddEditCarEvent.OnNameChange(it)) },
                placeholder = {
                    Text(text = "Name")
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            //TODO year picker

            TextField(
                value = viewModel.age,
                onValueChange = { viewModel.onEvent(AddEditCarEvent.OnAgeChange(it.toInt())) },
                placeholder = {
                    Text(text = "Age")
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.vin,
                onValueChange = { viewModel.onEvent(AddEditCarEvent.OnVinChange(it)) },
                placeholder = {
                    Text(text = "VIN")
                }
            )
        }
    }
}