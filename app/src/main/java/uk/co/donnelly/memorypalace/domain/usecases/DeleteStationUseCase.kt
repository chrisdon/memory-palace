package uk.co.donnelly.memorypalace.domain.usecases

import uk.co.donnelly.memorypalace.domain.entities.Station
import uk.co.donnelly.memorypalace.domain.repositories.StationRepository

class DeleteStationUseCase(private val stationRepository: StationRepository) {
    suspend operator fun invoke(station: Station) = stationRepository.removeStation(station)
}