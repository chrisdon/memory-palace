package uk.co.donnelly.memorypalace.data.station

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import uk.co.donnelly.memorypalace.domain.entities.Station

interface StationDataSource {
    suspend fun saveStation(station: Station)
    suspend fun deleteStation(station: Station)
    suspend fun getStations(palaceId: Long): Flow<List<Station>>
    suspend fun getStation(stationId: Long): Flow<Station?>
}

class StationDataSourceImpl(
    private val stationDao: StationDao,
    private val dispatcher: CoroutineDispatcher,
    private val stationEntityMapper: StationMapper
) : StationDataSource {
    override suspend fun saveStation(station: Station) = withContext(dispatcher) {
        stationDao.insertOrUpdate(stationEntityMapper.toStationEntity(station))
    }

    override suspend fun deleteStation(station: Station) {
        stationDao.deleteStation(stationEntityMapper.toStationEntity(station))
    }

    override suspend fun getStations(palaceId: Long): Flow<List<Station>> {
        val savedStationsFlow = stationDao.getSavedStations(palaceId)
        return savedStationsFlow.map { list ->
            list.map { station ->
                stationEntityMapper.toStation(station)
            }
        }
    }

    override suspend fun getStation(stationId: Long): Flow<Station?> {
        return stationDao.getSavedStation(stationId).map { stationEntity: StationEntity? ->
            if (stationEntity != null){
                stationEntityMapper.toStation(stationEntity)
            } else {
                null
            }
        }
    }
}