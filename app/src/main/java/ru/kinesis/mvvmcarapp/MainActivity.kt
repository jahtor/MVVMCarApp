package ru.kinesis.mvvmcarapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import ru.kinesis.mvvmcarapp.ui.add_edit_car.AddEditCarScreen
import ru.kinesis.mvvmcarapp.ui.add_edit_repair.AddEditRepairScreen
import ru.kinesis.mvvmcarapp.ui.car_list.CarListScreen
import ru.kinesis.mvvmcarapp.ui.repair_list.RepairListScreen
import ru.kinesis.mvvmcarapp.ui.theme.MVVMCarAppTheme
import ru.kinesis.mvvmcarapp.util.Routes

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMCarAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Navigation()
                }
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.CAR_LIST
    ){
        composable(Routes.CAR_LIST){
            CarListScreen(
                onNavigate = { navController.navigate(it.route) }
            )
        }

        composable(
            route = Routes.ADD_EDIT_CAR + "?carId={carId}",
            arguments = listOf(
                navArgument(name = "carId"){
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ){
            AddEditCarScreen(onPopBackStack = { navController.popBackStack() })
        }

        composable(
            route = Routes.REPAIR_LIST + "?carId={carId}",
            arguments = listOf(
                navArgument(name = "carId"){
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ){
            RepairListScreen( onNavigate = { navController.navigate(it.route) })
        }

        composable(
            route = Routes.ADD_EDIT_REPAIR + "?carId={carId}" + "&repairId={repairId}",
            arguments = listOf(
                navArgument(name = "carId"){
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(name = "repairId"){
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ){
            AddEditRepairScreen(onPopBackStack = { navController.popBackStack() })
        }
    }
}