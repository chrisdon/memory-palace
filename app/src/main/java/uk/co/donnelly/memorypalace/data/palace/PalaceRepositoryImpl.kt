package uk.co.donnelly.memorypalace.data.palace

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
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

class PalaceRepositoryFake: PalaceRepository {
    private val palaces = mutableListOf<Palace>()
    override fun getPalaces(): Flow<List<Palace>> = flowOf(palaces)

    override suspend fun addPalace(palace: Palace) {
        palaces.add(palace)
    }

    override suspend fun updatePalace(palace: Palace) {
        val existingPalace = palaces.first { it.id == palace.id }
        val index = palaces.indexOf(existingPalace)
        palaces[index] = palace
    }

    override suspend fun removePalace(palace: Palace) {
        palaces.remove(palace)
    }

    override fun getPalace(palaceId: Long): Flow<Palace?> {
        return flowOf( palaces.find { it.id == palaceId })
    }
}