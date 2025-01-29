package uk.co.donnelly.memorypalace.domain.usecases

import uk.co.donnelly.memorypalace.domain.repositories.StationRepository

class GetStationsUseCase(private val stationRepository: StationRepository) {
    suspend operator fun invoke(palaceId: Long) = stationRepository.getStations(palaceId)
}