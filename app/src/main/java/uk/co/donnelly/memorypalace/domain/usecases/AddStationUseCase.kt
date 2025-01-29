package uk.co.donnelly.memorypalace.domain.usecases

import uk.co.donnelly.memorypalace.domain.entities.Station
import uk.co.donnelly.memorypalace.domain.repositories.StationRepository

class AddStationUseCase(private val stationRepository: StationRepository) {
    suspend operator fun invoke(station: Station) = stationRepository.addStation(station)
}