package com.kizune.tome.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.kizune.tome.network.Book
import com.kizune.tome.network.previewBook
import com.kizune.tome.utils.enterTransition
import com.kizune.tome.utils.exitTransition

enum class Screens {
    Dashboard,
    BookDetails,
    CategoryList
}

enum class ContentType {
    Compact,
    Medium,
    Expanded
}

enum class NavigationType {
    Bottom,
    Rail,
    Drawer,
}

/**
 * Entry point dell'app che si occupa della navigazione tra vari screen
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TomeApp(
    widthSize: WindowWidthSizeClass,
    viewModel: BookViewModel,
    onBackButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bookUiState = viewModel.bookUiState
    val bookSelectedUiState by viewModel.bookUiStateSelected.collectAsState()
    val bookCategoryUiState by viewModel.bookUiStateCategory.collectAsState()
    val bookViewModel: BookViewModel = viewModel(factory = BookViewModel.Factory)
    var showSecondaryPane by remember { mutableStateOf(false) }

    val navController = rememberAnimatedNavController()

    val onCardPressed: (Book) -> Unit = { book ->
        bookViewModel.updateBookSelected(book)
        if (widthSize != WindowWidthSizeClass.Expanded) {
            navController.navigate(Screens.BookDetails.name)
        } else {
            showSecondaryPane = false
        }
    }
    val onBackClicked: () -> Unit = {
        navController.navigateUp()
    }

    val onShowMoreClicked: (BookCategory) -> Unit = { category ->
        bookViewModel.updateCategorySelected(category)
        if (widthSize != WindowWidthSizeClass.Expanded) {
            navController.navigate(Screens.CategoryList.name)
        } else {
            showSecondaryPane = true
        }

    }

    val onRetryClicked: () -> Unit = {
        bookViewModel.getBooks()
    }

    val contentType: ContentType
    val navigationType: NavigationType

    when (widthSize) {
        WindowWidthSizeClass.Compact -> {
            contentType = ContentType.Compact
            navigationType = NavigationType.Bottom
        }
        WindowWidthSizeClass.Medium -> {
            contentType = ContentType.Medium
            navigationType = NavigationType.Rail
        }
        WindowWidthSizeClass.Expanded -> {
            contentType = ContentType.Expanded
            navigationType = NavigationType.Drawer
        }
        else -> {
            contentType = ContentType.Compact
            navigationType = NavigationType.Bottom
        }
    }

    AnimatedNavHost(
        navController = navController,
        startDestination = Screens.Dashboard.name,
        enterTransition = { enterTransition() },
        exitTransition = { exitTransition() },
        popEnterTransition = { enterTransition() },
        popExitTransition = { exitTransition() },
        modifier = modifier
    ) {
        composable(route = Screens.Dashboard.name) {
            DashboardScreen(
                bookUiState = bookUiState,
                bookSelected = bookSelectedUiState.book,
                bookCategory = bookCategoryUiState.category,
                onBackClicked = onBackButton,
                onShowMoreClicked = onShowMoreClicked,
                onCardPressed = onCardPressed,
                onRetryClicked = onRetryClicked,
                contentType = contentType,
                showSecondaryPane = showSecondaryPane,
                navigationType = navigationType,
                modifier = modifier
            )
        }
        composable(route = Screens.BookDetails.name) {
            BookScreen(
                book = bookSelectedUiState.book ?: previewBook,
                contentType = contentType,
                onBackClicked = onBackClicked
            )
        }
        composable(route = Screens.CategoryList.name) {
            CategoryScreen(
                bookUiState = bookUiState,
                bookCategory = bookCategoryUiState.category ?: BookCategory.Literature,
                onCardPressed = onCardPressed,
                onBackClicked = onBackClicked
            )
        }
    }

}