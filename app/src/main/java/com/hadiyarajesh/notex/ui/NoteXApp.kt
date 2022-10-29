package com.hadiyarajesh.notex.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hadiyarajesh.notex.ui.navigation.MainBottomBar
import com.hadiyarajesh.notex.ui.navigation.NoteXNavigation
import com.hadiyarajesh.notex.ui.navigation.bottomNavItems
import com.hadiyarajesh.notex.ui.theme.NoteXTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteXApp() {
    NoteXTheme {
        val navController = rememberNavController()
        // A state that maintains visibility of a bottom bar
        val bottomBarState = rememberSaveable { mutableStateOf(true) }

        Scaffold(
            bottomBar = {
                AnimatedVisibility(
                    visible = bottomBarState.value,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it }),
                ) {
                    MainBottomBar(
                        destinations = bottomNavItems,
                        onNavigateToDestination = { destination ->
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        currentDestination = navController.currentBackStackEntryAsState().value?.destination
                    )
                }
            }
        ) { innerPadding ->
            NoteXNavigation(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                bottomBarState = bottomBarState
            )
        }
    }
}
