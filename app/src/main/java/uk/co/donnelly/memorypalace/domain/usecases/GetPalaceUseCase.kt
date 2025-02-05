package uk.co.donnelly.memorypalace.domain.usecases

import uk.co.donnelly.memorypalace.domain.repositories.PalaceRepository

class GetPalaceUseCase(private val palaceRepository: PalaceRepository) {
    operator fun invoke(palaceId: Long) = palaceRepository.getPalace(palaceId)
}

