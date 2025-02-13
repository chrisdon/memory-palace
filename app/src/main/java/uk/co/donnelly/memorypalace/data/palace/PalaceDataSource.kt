package uk.co.donnelly.memorypalace.data.palace

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import uk.co.donnelly.memorypalace.data.common.ImageUtil
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
    private val palaceEntityMapper: PalaceMapper,
    private val imageUtil: ImageUtil
) : PalaceDataSource {
    override suspend fun savePalace(palace: Palace) = withContext(dispatcher) {
        val palaceToSave = storeImageAsFile(palace)
        palaceDao.insertOrUpdate(palaceEntityMapper.toPalaceEntity(palaceToSave))
    }

    override suspend fun updatePalace(palace: Palace) = withContext(dispatcher) {
        palaceDao.updatePalace(palaceEntityMapper.toPalaceEntity(palace))
    }

    override suspend fun deletePalace(palace: Palace) = withContext(dispatcher) {
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

    private fun storeImageAsFile(palace: Palace): Palace {
        return if (palace.imageUrl != null) {
            palace.copy(imageUrl = imageUtil.storeImageAsFile(palace.imageUrl, palace.id))
        } else {
            palace
        }
    }
}

class PalaceDataSourceFake : PalaceDataSource {
    private var palaceList: MutableList<Palace> = mutableListOf()
    override suspend fun savePalace(palace: Palace) {
        palaceList.add(palace)
    }

    override suspend fun updatePalace(palace: Palace) {
        val savedPalace = palaceList.find { it.id == palace.id }
        palaceList[palaceList.indexOf(savedPalace)] = palace
    }

    override suspend fun deletePalace(palace: Palace) {
        palaceList.remove(palace)
    }

    override fun getPalaces(): Flow<List<Palace>> {
        return flowOf(palaceList)
    }

    override fun getPalace(palaceId: Long): Flow<Palace?> {
        return flowOf(palaceList.find { it.id == palaceId })
    }
}