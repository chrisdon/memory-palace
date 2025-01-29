package uk.co.donnelly.memorypalace.data.palace

import kotlinx.coroutines.flow.Flow
import uk.co.donnelly.memorypalace.domain.entities.Palace

interface PalaceDataSource {
    suspend fun savePalace(palace: Palace)
    suspend fun updatePalace(palace: Palace)
    suspend fun deletePalace(palace: Palace)
    fun getPalaces(): Flow<List<Palace>>
    fun getPalace(palaceId: Long): Flow<Palace?>
}