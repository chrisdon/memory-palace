package uk.co.donnelly.memorypalace.data.station

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import uk.co.donnelly.memorypalace.domain.entities.Station
import uk.co.donnelly.memorypalace.domain.repositories.StationRepository

class StationRepositoryImpl(
    val stationDataSource: StationDataSource
) : StationRepository {
    override suspend fun getStations(palaceId: Long): Flow<List<Station>> {
        return stationDataSource.getStations(palaceId)
    }

    override suspend fun addStation(station: Station) {
        stationDataSource.saveStation(station)
    }

    override suspend fun removeStation(station: Station) {
        stationDataSource.deleteStation(station)
    }

    override suspend fun getStation(stationId: Long): Flow<Station?> {
        return stationDataSource.getStation(stationId)
    }
}

class StationRepositoryFake: StationRepository {
    private val stations = mutableListOf<Station>()

    override suspend fun getStations(palaceId: Long): Flow<List<Station>> = flowOf(stations)

    override suspend fun addStation(station: Station) {
        stations.add(station)
    }

    override suspend fun removeStation(station: Station) {
        stations.remove(station)
    }

    override suspend fun getStation(stationId: Long): Flow<Station?> {
        return flowOf(stations.find { it.id == stationId })
    }

}