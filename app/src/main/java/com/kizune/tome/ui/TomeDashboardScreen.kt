package com.kizune.tome.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kizune.tome.network.Book
import com.kizune.tome.network.previewBook
import com.kizune.tome.theme.TomeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    bookUiState: BookUiState,
    bookSelected: Book?,
    bookCategory: BookCategory?,
    onShowMoreClicked: (BookCategory) -> Unit,
    onCardPressed: (Book) -> Unit,
    onRetryClicked: () -> Unit,
    onBackClicked: () -> Unit,
    contentType: ContentType,
    showSecondaryPane: Boolean,
    navigationType: NavigationType,
    modifier: Modifier = Modifier
) {
    var selectedItem by rememberSaveable { mutableStateOf(0) }
    val navController = rememberNavController()

    val onNavigationItemClick: (Int) -> Unit = { index ->
        selectedItem = index
        navController.navigate(navigationItems[index].route) {
            navController.graph.startDestinationRoute?.let { route ->
                popUpTo(route) {
                    saveState = true
                }
            }
            restoreState = true
        }
    }

    Scaffold(
        bottomBar = {
            if (navigationType == NavigationType.Bottom) {
                BottomBar(
                    selectedItem = selectedItem,
                    onNavigationItemClick = onNavigationItemClick
                )
            }
        },
        content = { padding ->
            Row(modifier = Modifier.fillMaxSize()) {
                when (navigationType) {
                    NavigationType.Rail -> {
                        NavigationRail(
                            selectedItem = selectedItem,
                            onNavigationItemClick = onNavigationItemClick
                        )
                    }
                    NavigationType.Drawer -> {
                        NavigationDrawer(
                            selectedItem = selectedItem,
                            onNavigationItemClick = onNavigationItemClick,
                        )
                    }
                    else -> {}
                }
                NavigationHost(
                    navController = navController,
                    bookUiState = bookUiState,
                    bookSelected = bookSelected,
                    bookCategory = bookCategory,
                    padding = padding,
                    onShowMoreClicked = onShowMoreClicked,
                    onCardPressed = onCardPressed,
                    onRetryClicked = onRetryClicked,
                    onBackClicked = onBackClicked,
                    contentType = contentType,
                    showSecondaryPane = showSecondaryPane
                )
            }
        },
        modifier = modifier
    )
}

@Composable
fun NavigationHost(
    navController: NavHostController,
    bookUiState: BookUiState,
    bookSelected: Book?,
    bookCategory: BookCategory?,
    padding: PaddingValues,
    onShowMoreClicked: (BookCategory) -> Unit,
    onCardPressed: (Book) -> Unit,
    onRetryClicked: () -> Unit,
    onBackClicked: () -> Unit,
    contentType: ContentType,
    showSecondaryPane: Boolean,
) {
    NavHost(
        navController = navController,
        startDestination = DashboardScreens.Home.route
    ) {
        composable(route = DashboardScreens.Home.route) {
            HomeScreen(
                bookUiState = bookUiState,
                bookSelected = bookSelected,
                bookCategory = bookCategory,
                contentType = contentType,
                showSecondaryPane = showSecondaryPane,
                onShowMoreClicked = onShowMoreClicked,
                onCardPressed = onCardPressed,
                onRetryClicked = onRetryClicked,
                onBackClicked = onBackClicked,
                modifier = Modifier.padding(padding)
            )
        }
        composable(route = DashboardScreens.Research.route) {
            ResearchScreen(
                bookUiState = bookUiState,
                bookSelected = bookSelected,
                contentType = contentType,
                onCardPressed = onCardPressed,
                onRetryClicked = onRetryClicked,
                onBackClicked = onBackClicked,
                modifier = Modifier.padding(padding)
            )
        }
        composable(route = DashboardScreens.Profile.route) {
            ProfileScreen(
                contentType = contentType,
                onBackClicked = onBackClicked,
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenCompactPreview() {
    TomeTheme {
        DashboardScreen(
            bookUiState = BookUiState.Success(hashMapOf()),
            bookSelected = previewBook,
            bookCategory = BookCategory.Literature,
            onBackClicked = { },
            onShowMoreClicked = { },
            onCardPressed = { },
            onRetryClicked = { },
            contentType = ContentType.Compact,
            showSecondaryPane = false,
            navigationType = NavigationType.Bottom
        )
    }
}


@Preview(showBackground = true, widthDp = 700)
@Composable
fun DashboardScreenMediumPreview() {
    TomeTheme {
        DashboardScreen(
            bookUiState = BookUiState.Success(hashMapOf()),
            bookSelected = previewBook,
            bookCategory = BookCategory.Literature,
            onBackClicked = { },
            onShowMoreClicked = { },
            onCardPressed = { },
            onRetryClicked = { },
            contentType = ContentType.Medium,
            showSecondaryPane = false,
            navigationType = NavigationType.Rail
        )
    }
}

@Preview(showBackground = true, widthDp = 1200, heightDp = 800)
@Composable
fun DashboardScreenExpandedPreview() {
    TomeTheme {
        DashboardScreen(
            bookUiState = BookUiState.Success(hashMapOf()),
            bookSelected = previewBook,
            bookCategory = BookCategory.Literature,
            onBackClicked = { },
            onShowMoreClicked = { },
            onCardPressed = { },
            onRetryClicked = { },
            contentType = ContentType.Expanded,
            showSecondaryPane = false,
            navigationType = NavigationType.Drawer
        )
    }
}

