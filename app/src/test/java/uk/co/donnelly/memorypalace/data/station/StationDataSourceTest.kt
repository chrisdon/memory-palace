package uk.co.donnelly.memorypalace.data.station

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class StationDataSourceTest {
    val dao: StationDao = mock()
    val mapper = StationMapperImpl()

    @OptIn(ExperimentalCoroutinesApi::class)
    val sut = StationDataSourceImpl(
        stationDao = dao,
        dispatcher = UnconfinedTestDispatcher(),
        stationEntityMapper = mapper
    )

    @Test
    fun testSaveStation() = runTest {
        sut.saveStation(station)
        verify(dao).insertOrUpdate(stationEntity)
    }

    @Test
    fun testGetStation() = runTest {
        whenever(dao.getSavedStation(any())).thenReturn(flowOf(stationEntity))
        sut.getStation(0).collect { fetchedStation ->
            assertEquals(station, fetchedStation)
        }
    }

    @Test
    fun testGetStations() = runTest {
        whenever(dao.getSavedStations(any()))
            .thenReturn(flowOf(listOf(stationEntity, stationEntity2)))
        sut.getStations(0).collect { fetchedStations ->
            assertEquals(listOf(station, station2id0), fetchedStations)
        }
    }

    @Test
    fun testDeleteStation() = runTest {
        sut.deleteStation(station)
        verify(dao).deleteStation(stationEntity)
    }
}