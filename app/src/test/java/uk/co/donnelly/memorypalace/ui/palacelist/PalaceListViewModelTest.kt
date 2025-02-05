package uk.co.donnelly.memorypalace.ui.palacelist

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import uk.co.donnelly.memorypalace.data.palace.palaceDomain
import uk.co.donnelly.memorypalace.data.palace.palaceDomain2
import uk.co.donnelly.memorypalace.domain.entities.Palace
import uk.co.donnelly.memorypalace.domain.usecases.GetPalacesUseCase
import uk.co.donnelly.memorypalace.domain.usecases.GetPalacesUseCaseFake

@OptIn(ExperimentalCoroutinesApi::class)
class PalaceListViewModelTest {
    private val palaceFlow = MutableStateFlow<List<Palace>>(listOf())
    private val usecase: GetPalacesUseCase = GetPalacesUseCaseFake(palaceFlow)
    private val sut = PalaceListViewModel(usecase)

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher()) // !!!
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun verifyPalaceList() = runTest {
        val emitJob: Job?
        val list = listOf(palaceDomain, palaceDomain2)
        val expectedResult = PalaceListUiState.Success(list)
        val testResults = mutableListOf<PalaceListUiState>()

        emitJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.palacesFlow.toList(testResults)
        }

        runCurrent()
        palaceFlow.emit(list)
        runCurrent()
        assertEquals(2, testResults.size)
        assertEquals(expectedResult, testResults[1])
        emitJob.cancel()

    }
}