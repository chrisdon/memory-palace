package uk.co.donnelly.memorypalace.domain.usecases

import uk.co.donnelly.memorypalace.domain.repositories.StationRepository

class GetStationUseCase(private val stationRepository: StationRepository) {
    suspend operator fun invoke(stationId: Long) = stationRepository.getStation(stationId)
}