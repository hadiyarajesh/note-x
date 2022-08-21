package com.hadiyarajesh.notex.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.hadiyarajesh.notex.ui.navigation.NoteXNavigation
import com.hadiyarajesh.notex.ui.theme.NoteXTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteXApp() {
    NoteXTheme {
        val navController = rememberNavController()

        Scaffold { innerPadding ->
            NoteXNavigation(
                modifier = Modifier.padding(innerPadding),
                navController = navController
            )
        }
    }
}
