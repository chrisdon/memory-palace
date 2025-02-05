package uk.co.donnelly.memorypalace.domain.usecases

import kotlinx.coroutines.flow.Flow
import uk.co.donnelly.memorypalace.domain.entities.Palace
import uk.co.donnelly.memorypalace.domain.repositories.PalaceRepository

interface GetPalacesUseCase {
    operator fun invoke(): Flow<List<Palace>>
}

class GetPalacesUseCaseImpl(private val palaceRepository: PalaceRepository): GetPalacesUseCase {
    override operator fun invoke() = palaceRepository.getPalaces()
}

class GetPalacesUseCaseFake(private val palaceFlow: Flow<List<Palace>>) : GetPalacesUseCase {
    override fun invoke(): Flow<List<Palace>> {
        return palaceFlow
    }

}