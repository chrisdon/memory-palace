package uk.co.donnelly.memorypalace.domain.repositories

import kotlinx.coroutines.flow.Flow
import uk.co.donnelly.memorypalace.domain.entities.Palace

interface PalaceRepository {
    fun getPalaces(): Flow<List<Palace>>
    suspend fun addPalace(palace: Palace)
    suspend fun updatePalace(palace: Palace)
    suspend fun removePalace(palace: Palace)
    fun getPalace(palaceId: Long): Flow<Palace?>
}