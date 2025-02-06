package uk.co.donnelly.memorypalace.ui.palacelist

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.co.donnelly.memorypalace.R
import uk.co.donnelly.memorypalace.data.palace.palaceList
import uk.co.donnelly.memorypalace.ui.theme.MemoryPalaceTheme

@RunWith(AndroidJUnit4::class)
class PalaceListScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    val context = InstrumentationRegistry.getInstrumentation().targetContext

    private var onNewPalaceTapped: Int = 0
    private var onPalaceTapped: Int = 0

    private fun setupScreen(state: PalaceListUiState) {
        composeTestRule.setContent {
            MemoryPalaceTheme {
                PalaceListScreen(
                    palaceState = state,
                    onPalace = {
                        onPalaceTapped++
                    },
                    onNewPalace = {
                        onNewPalaceTapped++
                    }
                )
            }
        }
    }

    @Test
    fun verifyEmptyScreen() {
        setupScreen(PalaceListUiState.Success(listOf()))

        composeTestRule
            .onNodeWithText(context.getString(R.string.no_palaces))
            .assertExists()
    }

    @Test
    fun verifyFabTap() {
        setupScreen(PalaceListUiState.Success(palaceList))

        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.add_palace_fab))
            .performClick()

        assertEquals(1, onNewPalaceTapped)
    }

    @Test
    fun verifyItemTap() {
        setupScreen(PalaceListUiState.Success(palaceList))

        composeTestRule
            .onNodeWithText(palaceList[0].name, useUnmergedTree = true)
            .performClick()

        assertEquals(1, onPalaceTapped)
    }

    @Test
    fun verifyError() {
        setupScreen(PalaceListUiState.Error)

        composeTestRule
            .onNodeWithText(context.getString(R.string.error_message))
            .assertExists()
    }

    @Test
    fun verifyLoading() {
        setupScreen(PalaceListUiState.Loading)

        composeTestRule
            .onNodeWithTag(testTag = "Loading")
            .assertExists()
    }
}

