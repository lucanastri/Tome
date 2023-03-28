package com.kizune.tome.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.kizune.tome.FakeDataSource
import com.kizune.tome.theme.TomeTheme
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

/**
 * Testa alcuni dei filtri che sono presenti nel
 * Research Screen
 */
class TomeResearchContentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test_searchview_empty_input() {
        composeTestRule.setContent {
            TomeTheme {
                ResearchContent(books = FakeDataSource.books, onCardPressed = {})
            }
        }
        composeTestRule.onNodeWithTag("search_input").performTextInput("")
        composeTestRule.onNodeWithTag("id_2").assertIsDisplayed()
    }

    @Test
    fun test_searchview_filled_input() {
        composeTestRule.setContent {
            TomeTheme {
                ResearchContent(books = FakeDataSource.books, onCardPressed = {})
            }
        }
        composeTestRule.onNodeWithTag("search_input").performTextInput("Title1")
        composeTestRule.onNodeWithTag("id_1").assertIsDisplayed()
    }

    @Test
    fun test_searchview_filled_input_not() {
        composeTestRule.setContent {
            TomeTheme {
                ResearchContent(books = FakeDataSource.books, onCardPressed = {})
            }
        }
        composeTestRule.onNodeWithTag("search_input").performTextInput("Title1")
        composeTestRule.onNodeWithTag("id_2").assertDoesNotExist()
    }
}