package uk.co.donnelly.memorypalace.data.palace

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import uk.co.donnelly.memorypalace.domain.entities.Palace

interface PalaceDataSource {
    suspend fun savePalace(palace: Palace)
    suspend fun updatePalace(palace: Palace)
    suspend fun deletePalace(palace: Palace)
    fun getPalaces(): Flow<List<Palace>>
    fun getPalace(palaceId: Long): Flow<Palace?>
}

class PalaceDataSourceImpl(
    private val palaceDao: PalaceDao,
    private val dispatcher: CoroutineDispatcher,
    private val palaceEntityMapper: PalaceMapper
) : PalaceDataSource {
    override suspend fun savePalace(palace: Palace) = withContext(dispatcher) {
        palaceDao.insertOrUpdate(palaceEntityMapper.toPalaceEntity(palace))
    }

    override suspend fun updatePalace(palace: Palace) {
        palaceDao.updatePalace(palaceEntityMapper.toPalaceEntity(palace))
    }

    override suspend fun deletePalace(palace: Palace) {
        palaceDao.deletePalace(palaceEntityMapper.toPalaceEntity(palace))
    }

    override fun getPalaces(): Flow<List<Palace>> {
        val savedPalacesFlow = palaceDao.getSavedPalaces()
        return savedPalacesFlow.map { list ->
            list.map { palace ->
                palaceEntityMapper.toPalace(palace)
            }
        }
    }

    override fun getPalace(palaceId: Long): Flow<Palace?> {
        return palaceDao.getSavedPalace(palaceId).map { value: PalaceEntity? ->
            if (value != null) {
                palaceEntityMapper.toPalace(value)
            } else {
                null
            }
        }
    }
}