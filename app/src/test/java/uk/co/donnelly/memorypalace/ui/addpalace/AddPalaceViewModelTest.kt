package uk.co.donnelly.memorypalace.ui.addpalace

import app.cash.turbine.test
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import uk.co.donnelly.memorypalace.data.palace.PalaceDataSourceFake
import uk.co.donnelly.memorypalace.data.palace.PalaceRepositoryImpl
import uk.co.donnelly.memorypalace.data.palace.palaceDomain
import uk.co.donnelly.memorypalace.domain.usecases.AddPalaceUseCase

@OptIn(ExperimentalCoroutinesApi::class)
class AddPalaceViewModelTest {
    private val dataSource = PalaceDataSourceFake()
    private val repository = PalaceRepositoryImpl(dataSource)
    private val usecase = AddPalaceUseCase(repository)
    private val sut = AddPalaceViewModel(usecase)

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher()) // !!!
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun verifyAddPalace() = runTest {
        sut.savePalace(palaceDomain)
        delay(250)
        dataSource.getPalace(2).test {
            assertEquals(palaceDomain, awaitItem())
            awaitComplete()
        }
    }
}