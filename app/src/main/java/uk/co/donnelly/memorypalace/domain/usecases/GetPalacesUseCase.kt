package uk.co.donnelly.memorypalace.domain.usecases

import uk.co.donnelly.memorypalace.domain.repositories.PalaceRepository

class GetPalacesUseCase(private val palaceRepository: PalaceRepository) {
    operator fun invoke() = palaceRepository.getPalaces()
}