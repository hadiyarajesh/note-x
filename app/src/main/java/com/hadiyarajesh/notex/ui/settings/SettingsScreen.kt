package com.hadiyarajesh.notex.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hadiyarajesh.notex.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController
) {
    val settingsItems = listOf(
        SettingsItems.Notifications,
        SettingsItems.Sounds,
        SettingsItems.Customize,
        SettingsItems.Language,
        SettingsItems.Widgets,
        SettingsItems.About,
        SettingsItems.Version
    )

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = "Settings")
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
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(settingsItems) { item ->
                    SettingsItem(item = item)
                }
            }
        })
}

@Composable
fun SettingsItem(item: SettingsItems) {
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
            Spacer(modifier = Modifier.width(18.dp))
            Icon(
                painter = painterResource(item.icon), contentDescription = "itemIcon",
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(32.dp))


            Text(
                text = item.name,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun SettingsItemPreview() {
    Surface {
        SettingsItem(SettingsItems.Notifications)
    }
}