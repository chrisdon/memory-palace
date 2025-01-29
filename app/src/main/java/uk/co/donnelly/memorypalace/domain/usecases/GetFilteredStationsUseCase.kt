package uk.co.donnelly.memorypalace.domain.usecases

import kotlinx.coroutines.flow.map
import uk.co.donnelly.memorypalace.domain.repositories.StationRepository

class GetFilteredStationsUseCase(private val stationRepository: StationRepository) {
    suspend operator fun invoke(palaceId: Long, channelId: Long) = if (channelId == -1L) {
        stationRepository.getStations(palaceId)
    } else {
        stationRepository.getStations(palaceId)
            .map { it.filter { item -> item.channelTypeId == channelId } }
    }
}