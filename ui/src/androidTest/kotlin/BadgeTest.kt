package kiwi.orbit.compose.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import kiwi.orbit.compose.ui.controls.BadgeInfo
import kiwi.orbit.compose.ui.controls.Text
import org.junit.Rule
import org.junit.Test

internal class BadgeTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBasics() {
        composeTestRule.setContent {
            BadgeInfo {
                Text("Latest Update", Modifier.testTag("Badge"))
            }
        }
        composeTestRule.onNodeWithTag("Badge").assertTextContains("Latest Update")
    }
}
