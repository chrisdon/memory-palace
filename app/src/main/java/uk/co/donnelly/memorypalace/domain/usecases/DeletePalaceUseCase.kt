package uk.co.donnelly.memorypalace.domain.usecases

import uk.co.donnelly.memorypalace.domain.entities.Palace
import uk.co.donnelly.memorypalace.domain.repositories.PalaceRepository

class DeletePalaceUseCase(private val palaceRepository: PalaceRepository) {
    suspend operator fun invoke(palace: Palace) = palaceRepository.removePalace(palace)
}