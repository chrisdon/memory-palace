package uk.co.donnelly.memorypalace.data.station

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class StationRepositoryTest {
    private lateinit var dataSource: StationDataSource
    private lateinit var sut: StationRepositoryImpl

    @Before
    fun setup() {
        dataSource = StationDataSourceFake()
        sut = StationRepositoryImpl(dataSource)
    }

    @Test
    fun verifyGetStations() = runTest {
        val expectedStations = listOf(station, station1)
        dataSource.saveStation(station)
        dataSource.saveStation(station1)

        sut.getStations(station.palaceId).collect { fetchedStations ->
            assertEquals(expectedStations, fetchedStations)
        }
    }

    @Test
    fun verifyAddStation() = runTest {
        val expectedStation = station

        sut.addStation(expectedStation)

        dataSource.getStation(station.id).collect { saveStation ->
            assertEquals(expectedStation, saveStation)
        }
    }

    @Test
    fun verifyRemoveStation() = runTest {
        dataSource.saveStation(station)
        dataSource.saveStation(station1)

        sut.removeStation(station1)

        dataSource.getStations(station.palaceId).collect { stations ->
            assertEquals(1, stations.size)
            assertEquals(0, stations[0].id)
        }
    }

    @Test
    fun verifyGetStation() = runTest {
        dataSource.saveStation(station)

        sut.getStation(station.id).collect { fetchedStation ->
            assertEquals(station, fetchedStation)
        }
    }
}