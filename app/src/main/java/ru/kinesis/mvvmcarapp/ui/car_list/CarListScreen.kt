package ru.kinesis.mvvmcarapp.ui.car_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collect
import ru.kinesis.mvvmcarapp.util.UiEvent

@Composable
fun CarListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: CarListViewModel = hiltViewModel()
) {
    val cars = viewModel.cars.collectAsState(initial = emptyList())
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
                        viewModel.onEvent(CarListEvent.OnUndoDeleteClick)
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
                viewModel.onEvent(CarListEvent.OnAddCarClick)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("CARS LIST") },
//                backgroundColor = MaterialTheme.colors.surface
                backgroundColor = Color.LightGray
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            items(cars.value){ car ->
                CarItem(
                    car = car,
                    onEvent = viewModel::onEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onEvent(CarListEvent.OnCarClick(car))
                        }
                        .padding(8.dp)
                )
            }
        }
    }
}