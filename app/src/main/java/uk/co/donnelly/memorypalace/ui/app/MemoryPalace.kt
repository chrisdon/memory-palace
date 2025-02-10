package uk.co.donnelly.memorypalace.ui.app

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import uk.co.donnelly.memorypalace.ui.addpalace.AddPalaceScreen
import uk.co.donnelly.memorypalace.ui.addpalace.AddPalaceViewModel
import uk.co.donnelly.memorypalace.ui.home.HomeScreen
import uk.co.donnelly.memorypalace.ui.palacelist.PalaceListScreen
import uk.co.donnelly.memorypalace.ui.palacelist.PalaceListViewModel

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)

@Serializable
object Home

@Serializable
object PalaceList

@Serializable
object AddPalace

val topLevelRoutes = listOf(
    TopLevelRoute("Home", Home, Icons.Default.Home),
    TopLevelRoute("Palaces", PalaceList, Icons.Default.Menu)
)

@Composable
fun MemoryPalace(
    navController: NavHostController = rememberNavController()
) {
    var navigationSelectedItem by remember {
        mutableStateOf(0)
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                topLevelRoutes.forEachIndexed { index, navigationItem ->
                    NavigationBarItem(
                        selected = index == navigationSelectedItem,
                        label = { Text(navigationItem.name) },
                        icon = {
                            Icon(
                                navigationItem.icon,
                                contentDescription = navigationItem.name
                            )
                        },
                        onClick = {
                            navigationSelectedItem = index
                            navController.navigate(navigationItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Home,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Home> {
                HomeScreen()
            }
            composable<PalaceList> {
                val viewModel = hiltViewModel<PalaceListViewModel>()
                val palaceState by viewModel.palacesFlow.collectAsState()
                PalaceListScreen(
                    palaceState = palaceState,
                    onNewPalace = {
                        navController.navigate(AddPalace)
                    },
                    onSelectedPalace = { palace ->

                    }
                )
            }
            composable<AddPalace> {
                val viewModel = hiltViewModel<AddPalaceViewModel>()
                AddPalaceScreen(
                    onAddPalace = { palace ->
                        viewModel.savePalace(palace)
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
