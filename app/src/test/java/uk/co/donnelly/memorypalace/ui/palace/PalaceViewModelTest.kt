package uk.co.donnelly.memorypalace.ui.palace

import app.cash.turbine.test
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import uk.co.donnelly.memorypalace.MainCoroutineRule
import uk.co.donnelly.memorypalace.data.channeltype.ChannelTypeRepositoryFake
import uk.co.donnelly.memorypalace.data.channeltype.channelTypeDomainFrench
import uk.co.donnelly.memorypalace.data.channeltype.channelTypeDomainGerman
import uk.co.donnelly.memorypalace.data.palace.PalaceRepositoryFake
import uk.co.donnelly.memorypalace.data.palace.palaceDomainId0
import uk.co.donnelly.memorypalace.data.station.StationMapperImpl
import uk.co.donnelly.memorypalace.data.station.StationRepositoryFake
import uk.co.donnelly.memorypalace.data.station.station1
import uk.co.donnelly.memorypalace.data.station.station2
import uk.co.donnelly.memorypalace.domain.usecases.AddPalaceUseCase
import uk.co.donnelly.memorypalace.domain.usecases.DeletePalaceUseCase
import uk.co.donnelly.memorypalace.domain.usecases.GetChannelTypesUseCase
import uk.co.donnelly.memorypalace.domain.usecases.GetPalaceUseCase
import uk.co.donnelly.memorypalace.domain.usecases.GetStationsUseCase
import uk.co.donnelly.memorypalace.ui.station.StationDisplay

@OptIn(ExperimentalCoroutinesApi::class)
class PalaceViewModelTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var palaceRepositoryFake: PalaceRepositoryFake
    private lateinit var stationRepositoryFake: StationRepositoryFake
    private lateinit var channelTypeRepositoryFake: ChannelTypeRepositoryFake
    private val addPalaceUseCase by lazy { AddPalaceUseCase(palaceRepositoryFake) }
    private val getPalaceUseCase by lazy { GetPalaceUseCase(palaceRepositoryFake) }
    private val getStationsUseCase by lazy { GetStationsUseCase(stationRepositoryFake) }
    private val deletePalaceUseCase by lazy { DeletePalaceUseCase(palaceRepositoryFake) }
    private val getChannelTypesUseCase by lazy { GetChannelTypesUseCase(channelTypeRepositoryFake) }
    val sut by lazy {
        PalaceViewModel(
            addPalaceUseCase,
            getPalaceUseCase,
            getStationsUseCase,
            deletePalaceUseCase,
            getChannelTypesUseCase,
            StationMapperImpl()
        )
    }

    @Before
    fun setup() {
        palaceRepositoryFake = PalaceRepositoryFake()
        stationRepositoryFake = StationRepositoryFake()
        channelTypeRepositoryFake = ChannelTypeRepositoryFake()
    }

    @Test
    fun verifyGetPalaceSuccess() = runTest {
        // Given
        palaceRepositoryFake.addPalace(palaceDomainId0)
        stationRepositoryFake.addStation(station1)
        stationRepositoryFake.addStation(station2)
        channelTypeRepositoryFake.addChannelType(channelTypeDomainFrench)
        channelTypeRepositoryFake.addChannelType(channelTypeDomainGerman)
        val expectedStations = listOf(
            StationDisplay(station1, channelTypeDomainGerman),
            StationDisplay(station2, channelTypeDomainGerman)
        )
        val expectedState = PalaceUiState.Success(palaceDomainId0, expectedStations)
        // When
        sut.getPalace(palaceDomainId0.id)
        advanceUntilIdle()
        // Then
        assertEquals(expectedState, sut.uiState.value)
    }

    @Test
    fun verifyGetPalaceError() = runTest {
        // Given
        val palaceId = 0L
        // When
        sut.getPalace(palaceId)
        advanceUntilIdle()
        // Then
        assertEquals(
            PalaceUiState.Error(Exception("No value found for palace id: $palaceId")),
            sut.uiState.value
        )
    }

    @Test
    fun verifySavePalace() = runTest {
        // Given no setup needed
        // When
        sut.savePalace(palaceDomainId0)
        advanceUntilIdle()
        // Then
        palaceRepositoryFake.getPalace(palaceDomainId0.id).test {
            assertEquals(palaceDomainId0, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun verifyDeletePalace() = runTest {
        // Given
        palaceRepositoryFake.addPalace(palaceDomainId0)
        val palaceId = palaceDomainId0.id
        // When
        sut.deletePalace(palaceDomainId0)
        delay(250)
        sut.getPalace(palaceDomainId0.id)
        advanceUntilIdle()
        // Then
        assertEquals(
            PalaceUiState.Error(Exception("No value found for palace id: $palaceId")),
            sut.uiState.value
        )
    }
}