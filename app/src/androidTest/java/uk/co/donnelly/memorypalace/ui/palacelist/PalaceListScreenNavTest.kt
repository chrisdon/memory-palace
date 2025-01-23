package uk.co.donnelly.memorypalace.ui.palacelist

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.co.donnelly.memorypalace.ui.app.MemoryPalace
import uk.co.donnelly.memorypalace.ui.app.PalaceList

@RunWith(AndroidJUnit4::class)
class PalaceListScreenNavTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Before
    fun setupMemoryPalace() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            MemoryPalace(navController = navController)
        }
    }

    @Test
    fun verifyNavigation() {
        composeTestRule.onNodeWithText("Palaces").performClick()
        assertTrue(navController.currentBackStackEntry?.destination?.hasRoute<PalaceList>() ?: false)
    }
}