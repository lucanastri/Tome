package com.kizune.tome.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kizune.tome.R

sealed class DashboardScreens(
    val route: String,
    @StringRes val resourceId: Int,
    val imageVector: ImageVector
) {
    object Home : DashboardScreens("Home", R.string.home, Icons.Rounded.Home)
    object Research : DashboardScreens("Research", R.string.research, Icons.Rounded.Search)
    object Profile : DashboardScreens("Profile", R.string.profile, Icons.Rounded.AccountBox)
}

val navigationItems = listOf(
    DashboardScreens.Home,
    DashboardScreens.Research,
    DashboardScreens.Profile
)

@Composable
fun BottomBar(
    selectedItem: Int,
    onNavigationItemClick: (Int) -> Unit,
) {
    NavigationBar {
        navigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.imageVector,
                        contentDescription = stringResource(id = item.resourceId)
                    )
                },
                label = { Text(text = stringResource(id = item.resourceId)) },
                selected = index == selectedItem,
                onClick = {
                    onNavigationItemClick(index)
                }
            )
        }
    }
}

@Composable
fun NavigationRail(
    selectedItem: Int,
    onNavigationItemClick: (Int) -> Unit,
) {
    NavigationRail(
        containerColor = colorResource(id = R.color.surface2)
    ) {
        Spacer(Modifier.height(16.dp))
        navigationItems.forEachIndexed { index, item ->
            NavigationRailItem(
                icon = {
                    Icon(
                        imageVector = item.imageVector,
                        contentDescription = stringResource(id = item.resourceId)
                    )
                },
                label = { Text(text = stringResource(id = item.resourceId)) },
                selected = index == selectedItem,
                onClick = {
                    onNavigationItemClick(index)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(
    selectedItem: Int,
    onNavigationItemClick: (Int) -> Unit,
) {
    PermanentNavigationDrawer(
        modifier = Modifier.width(300.dp),
        drawerContent = {
            PermanentDrawerSheet(
                drawerContainerColor = colorResource(id = R.color.surface1),
                drawerContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                drawerShape = RoundedCornerShape(bottomEnd = 16.dp)
            ) {
                Spacer(Modifier.height(32.dp))
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = stringResource(id = R.string.drawer_title),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
                Spacer(Modifier.height(32.dp))
                navigationItems.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            unselectedContainerColor = Color.Transparent,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        ),
                        icon = {
                            Icon(
                                imageVector = item.imageVector,
                                contentDescription = stringResource(id = item.resourceId)
                            )
                        },
                        label = { Text(text = stringResource(id = item.resourceId)) },
                        selected = index == selectedItem,
                        onClick = {
                            onNavigationItemClick(index)
                        },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    ) {

    }
}