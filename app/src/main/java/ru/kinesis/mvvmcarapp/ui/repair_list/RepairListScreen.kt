package ru.kinesis.mvvmcarapp.ui.repair_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collect
import ru.kinesis.mvvmcarapp.util.UiEvent

@Composable
fun RepairListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: RepairListViewModel = hiltViewModel()
) {
    val repairs = viewModel.repairs.collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect { event ->
            when(event){
                is UiEvent.ShowSnackbar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if (result == SnackbarResult.ActionPerformed){
                        viewModel.onEvent(RepairListEvent.OnUndoDeleteClick)
                    }
                }
                is UiEvent.Navigate -> onNavigate(event)
                else  -> Unit
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(RepairListEvent.OnAddRepairClick(viewModel.carId))
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
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
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            items(repairs.value){ repair ->
                RepairItem(
                    repair = repair,
                    onEvent = viewModel::onEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onEvent(RepairListEvent.OnRepairClick(repair))
                        }
                        .padding(8.dp)
                )
            }
        }
    }
}