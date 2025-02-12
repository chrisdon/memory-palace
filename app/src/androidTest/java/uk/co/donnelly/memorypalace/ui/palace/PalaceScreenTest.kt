package uk.co.donnelly.memorypalace.ui.palace

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
import uk.co.donnelly.memorypalace.data.palace.palaceDomainId0
import uk.co.donnelly.memorypalace.data.station.station1Domain
import uk.co.donnelly.memorypalace.data.station.station2Domain
import uk.co.donnelly.memorypalace.data.station.stationDisplay1
import uk.co.donnelly.memorypalace.data.station.stationDisplay2
import uk.co.donnelly.memorypalace.domain.entities.Palace
import uk.co.donnelly.memorypalace.ui.common.TAG_LOADING
import uk.co.donnelly.memorypalace.ui.theme.MemoryPalaceTheme

@RunWith(AndroidJUnit4::class)
class PalaceScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private var onSavedPalace: Palace? = null
    private var onDeletedPalace: Palace? = null

    @Test
    fun verifyDisplayModeNoStations() {
        setupScreen(PalaceUiState.Success(palaceDomainId0, listOf()))

        composeTestRule.onNodeWithText(palaceDomainId0.name).assertExists()
        composeTestRule.onNodeWithText(context.getString(R.string.no_stations)).assertExists()
    }

    @Test
    fun verifyLoading() {
        setupScreen(PalaceUiState.Loading)

        composeTestRule.onNodeWithText(context.getString(R.string.title_loading)).assertExists()
        composeTestRule.onNodeWithTag(testTag = TAG_LOADING).assertExists()
    }

    @Test
    fun verifyError() {
        setupScreen(PalaceUiState.Error(Exception("What is this?")))

        composeTestRule.onNodeWithText(context.getString(R.string.error_message)).assertExists()
    }

    @Test
    fun verifyDisplayModeStations() {
        setupScreen(PalaceUiState.Success(palaceDomainId0, listOf(stationDisplay1, stationDisplay2)))

        composeTestRule.onNodeWithText(palaceDomainId0.name).assertExists()
        composeTestRule.onNodeWithText(station1Domain.name).assertExists()
        composeTestRule.onNodeWithText(station2Domain.name).assertExists()
    }

    @Test
    fun verifyEditModeTitleInputField() {
        setupScreen(PalaceUiState.Success(palaceDomainId0, listOf()))

        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.edit_palace_content_desc))
            .performClick()

        composeTestRule.onNodeWithTag(TAG_PALACE_INPUT_FIELD).assertExists()
    }

    @Test
    fun verifyDeleteWarning() {
        setupScreen(PalaceUiState.Success(palaceDomainId0, listOf()))

        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.delete_palace_content_desc))
            .performClick()

        composeTestRule.onNodeWithText(context.getString(R.string.delete_popup_text))
        composeTestRule
            .onNodeWithText(context.getString(R.string.delete_popup_positive_button_text))
            .performClick()

        assertEquals(palaceDomainId0, onDeletedPalace)
    }

    private fun setupScreen(palaceUiState: PalaceUiState) {
        composeTestRule.setContent {
            MemoryPalaceTheme {
                PalaceScreen(
                    palaceState = palaceUiState,
                    onSavePalace = { savedPalace ->
                        onSavedPalace = savedPalace
                    },
                    onDeletePalace = { deletedPalace ->
                        onDeletedPalace = deletedPalace
                    }
                )
            }
        }
    }
}