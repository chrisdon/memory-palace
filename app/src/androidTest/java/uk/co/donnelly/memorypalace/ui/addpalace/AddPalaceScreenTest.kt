package uk.co.donnelly.memorypalace.ui.addpalace

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import uk.co.donnelly.memorypalace.R
import uk.co.donnelly.memorypalace.domain.entities.Palace
import uk.co.donnelly.memorypalace.ui.theme.MemoryPalaceTheme

class AddPalaceScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val inputLabel = context.getString(R.string.add_palace_name)
    private val savePalaceContentDesc = context.getString(R.string.save_palace_content_desc)
    private val expectedText = "Croydon"

    private var onPalaceSaved: Palace? = null

    @Test
    fun verifyTextContent() {
        composeTestRule.onNodeWithText(inputLabel).assertExists()
        composeTestRule.onNodeWithTag(TAG_INPUT_NAME).assertExists()
    }

    @Test
    fun verifySavePalace() {
        composeTestRule.onNodeWithTag(TAG_INPUT_NAME).performTextInput(expectedText)
        composeTestRule.onNodeWithContentDescription(savePalaceContentDesc).performClick()

        assertNotNull(onPalaceSaved)
        assertEquals(expectedText, onPalaceSaved?.name)
    }

    @Before
    fun setup() {
        composeTestRule.setContent {
            MemoryPalaceTheme {
                AddPalaceScreen(
                    onAddPalace = { addedPalace ->
                        onPalaceSaved = addedPalace
                    }
                )
            }
        }
    }
}