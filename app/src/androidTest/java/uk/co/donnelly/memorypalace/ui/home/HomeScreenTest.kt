package uk.co.donnelly.memorypalace.ui.home

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.co.donnelly.memorypalace.R
import uk.co.donnelly.memorypalace.ui.theme.MemoryPalaceTheme

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    @Ignore("Rewrite to check for text")
    fun verifyCardTap() {
        setupScreen()
        composeTestRule.onNodeWithText(context.getString(R.string.palaces)).performClick()
    }

    private fun setupScreen() {
        composeTestRule.setContent {
            MemoryPalaceTheme {
                HomeScreen()
            }
        }
    }
}