package uk.co.donnelly.memorypalace.data.station

import junit.framework.TestCase.assertEquals
import org.junit.Test

class StationMapperTest {
    val sut = StationMapperImpl()

    @Test
    fun testToDomain() {
        val domainEntity = sut.toStation(stationEntity)
        assertEquals(station, domainEntity)
    }

    @Test
    fun testToData() {
        val dataEntity = sut.toStationEntity(station)
        assertEquals(stationEntity, dataEntity)
    }

    @Test
    fun testToDataNoId() {
        val dataEntity = sut.toStationEntity(station2)
        assertEquals(stationEntity2, dataEntity)
    }
}