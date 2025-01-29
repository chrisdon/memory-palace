package uk.co.donnelly.memorypalace.domain.repositories

import kotlinx.coroutines.flow.Flow
import uk.co.donnelly.memorypalace.domain.entities.Station

interface StationRepository {
    suspend fun getStations(palaceId: Long): Flow<List<Station>>
    suspend fun addStation(station: Station)
    suspend fun removeStation(station: Station)
    suspend fun getStation(stationId: Long): Flow<Station?>
}