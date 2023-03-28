package com.kizune.tome.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.kizune.tome.theme.TomeTheme
import org.junit.Rule
import org.junit.Test

class TomeRatingBarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test_rating_bar_normal() {
        composeTestRule.setContent {
            TomeTheme {
                RatingBar(rating = 3.0, stars = 5)
            }
        }

        composeTestRule.onNodeWithTag("3_filled").assertIsDisplayed()
    }

    @Test
    fun test_rating_bar_normal_unfilled() {
        composeTestRule.setContent {
            TomeTheme {
                RatingBar(rating = 3.0, stars = 5, showUnfilledStar = true)
            }
        }

        composeTestRule.onNodeWithTag("3_filled").assertIsDisplayed()
        composeTestRule.onNodeWithTag("2_unfilled").assertIsDisplayed()
    }

    @Test
    fun test_rating_bar_overflow() {
        composeTestRule.setContent {
            TomeTheme {
                RatingBar(rating = 8.0, stars = 5)
            }
        }

        composeTestRule.onNodeWithTag("5_filled").assertIsDisplayed()
        composeTestRule.onNodeWithTag("8_filled").assertDoesNotExist()
    }

    @Test
    fun test_rating_bar_overflow_unfilled() {
        composeTestRule.setContent {
            TomeTheme {
                RatingBar(rating = 8.0, stars = 5, showUnfilledStar = true)
            }
        }

        composeTestRule.onNodeWithTag("5_filled").assertIsDisplayed()
        composeTestRule.onNodeWithTag("5_unfilled").assertDoesNotExist()
    }

    @Test
    fun test_rating_bar_underflow() {
        composeTestRule.setContent {
            TomeTheme {
                RatingBar(rating = -8.0, stars = 5)
            }
        }

        composeTestRule.onNodeWithTag("1_filled").assertDoesNotExist()
    }

    @Test
    fun test_rating_bar_underflow_unfilled() {
        composeTestRule.setContent {
            TomeTheme {
                RatingBar(rating = -8.0, stars = 5, showUnfilledStar = true)
            }
        }

        composeTestRule.onNodeWithTag("1_filled").assertDoesNotExist()
        composeTestRule.onNodeWithTag("5_unfilled").assertIsDisplayed()
    }
}