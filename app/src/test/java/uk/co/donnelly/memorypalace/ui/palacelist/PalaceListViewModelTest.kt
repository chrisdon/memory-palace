package uk.co.donnelly.memorypalace.ui.palacelist

import app.cash.turbine.test
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import uk.co.donnelly.memorypalace.MainCoroutineRule
import uk.co.donnelly.memorypalace.data.palace.palaceDomain
import uk.co.donnelly.memorypalace.data.palace.palaceDomain2
import uk.co.donnelly.memorypalace.domain.entities.Palace
import uk.co.donnelly.memorypalace.domain.usecases.GetPalacesUseCase
import uk.co.donnelly.memorypalace.domain.usecases.GetPalacesUseCaseFake

@OptIn(ExperimentalCoroutinesApi::class)
class PalaceListViewModelTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val palaceFlow = MutableStateFlow<List<Palace>>(listOf())
    private val usecase: GetPalacesUseCase = GetPalacesUseCaseFake(palaceFlow)
    private val sut = PalaceListViewModel(usecase)

    @Test
    @Ignore("Fix broken test")
    fun verifyPalaceList() = runTest {
        val list = listOf(palaceDomain, palaceDomain2)
        val expectedResult = PalaceListUiState.Success(list)

        palaceFlow.emit(list)
        advanceUntilIdle()

        assertEquals(expectedResult, sut.palacesFlow.value)
    }
}