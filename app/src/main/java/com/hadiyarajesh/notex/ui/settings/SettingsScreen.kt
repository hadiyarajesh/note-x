package com.hadiyarajesh.notex.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hadiyarajesh.notex.R
import com.hadiyarajesh.notex.ui.component.HorizontalSpacer
import com.hadiyarajesh.notex.ui.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = stringResource(R.string.settings))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                ),
            )
        }, content = {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(it)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SettingsItem(item = Screens.Notifications)
                SettingsItem(item = Screens.Sounds)
                SettingsItem(item = Screens.Customize)
                SettingsItem(item = Screens.Language)
                SettingsItem(item = Screens.Widgets)
                SettingsItem(item = Screens.About)
                SettingsItem(item = Screens.Version)
            }
        })
}

@Composable
fun SettingsItem(item: Screens) {
    Card(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row(
            Modifier
                .height(80.dp)
                .width(300.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            HorizontalSpacer(size = 18)
            Icon(
                painter = painterResource(item.icon), contentDescription = "itemIcon",
                modifier = Modifier.size(24.dp)
            )
            HorizontalSpacer(size = 32)
            Text(
                text = item.route,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun SettingsItemPreview() {
    Surface {
        SettingsItem(Screens.Notifications)
    }
}