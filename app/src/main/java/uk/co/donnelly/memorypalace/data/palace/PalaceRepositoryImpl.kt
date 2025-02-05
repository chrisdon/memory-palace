package uk.co.donnelly.memorypalace.data.palace

import kotlinx.coroutines.flow.Flow
import uk.co.donnelly.memorypalace.domain.entities.Palace
import uk.co.donnelly.memorypalace.domain.repositories.PalaceRepository

class PalaceRepositoryImpl(
    private val palaceDataSource: PalaceDataSource
) : PalaceRepository {
    override fun getPalaces(): Flow<List<Palace>> {
        return palaceDataSource.getPalaces()
    }

    override suspend fun addPalace(palace: Palace) {
        palaceDataSource.savePalace(palace)
    }

    override suspend fun updatePalace(palace: Palace) {
        palaceDataSource.updatePalace(palace)
    }

    override suspend fun removePalace(palace: Palace) {
        palaceDataSource.deletePalace(palace)
    }

    override fun getPalace(palaceId: Long): Flow<Palace?> {
        return palaceDataSource.getPalace(palaceId)
    }
}