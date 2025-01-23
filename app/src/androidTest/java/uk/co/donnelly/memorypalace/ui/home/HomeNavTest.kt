package uk.co.donnelly.memorypalace.ui.home

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.co.donnelly.memorypalace.ui.app.Home
import uk.co.donnelly.memorypalace.ui.app.MemoryPalace

@RunWith(AndroidJUnit4::class)
class HomeNavTest {
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
    fun verifyStartDestination() {
        assertTrue(navController.currentBackStackEntry?.destination?.hasRoute<Home>() ?: false)
    }
}