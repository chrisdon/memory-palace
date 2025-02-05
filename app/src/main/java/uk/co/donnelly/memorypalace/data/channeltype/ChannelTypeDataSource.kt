package uk.co.donnelly.memorypalace.data.channeltype

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import uk.co.donnelly.memorypalace.domain.entities.ChannelType

interface ChannelTypeDataSource {
    suspend fun saveChannelType(channelType: ChannelType)
    suspend fun deleteChannelType(channelType: ChannelType)
    fun getChannelTypes() : Flow<List<ChannelType>>
    fun getChannelType(channelTypeId: Long) : Flow<ChannelType?>
}

class ChannelTypeDataSourceImpl(
    private val channelTypeDao: ChannelTypeDao,
    private val dispatcher: CoroutineDispatcher,
    private val channelTypeEntityMapper: ChannelTypeMapper
) : ChannelTypeDataSource {
    override suspend fun saveChannelType(channelType: ChannelType) = withContext(dispatcher) {
        channelTypeDao.insertOrUpdate(channelTypeEntityMapper.toChannelTypeEntity(channelType))
    }

    override suspend fun deleteChannelType(channelType: ChannelType) = withContext(dispatcher) {
        channelTypeDao.deleteChannelType(channelTypeEntityMapper.toChannelTypeEntity(channelType))
    }

    override fun getChannelTypes(): Flow<List<ChannelType>> {
        return channelTypeDao.getSavedChannelTypes().map { channelTypes ->
            channelTypes.map { channelTypeEntity ->
                channelTypeEntityMapper.toChannelType(channelTypeEntity)
            }
        }
    }

    override fun getChannelType(channelTypeId: Long): Flow<ChannelType?> {
        return channelTypeDao.getSavedChannelType(channelTypeId).map { channelTypeEntity: ChannelTypeEntity? ->
            if (channelTypeEntity != null) {
                channelTypeEntityMapper.toChannelType(channelTypeEntity)
            } else {
                null
            }
        }
    }
}

class ChannelTypeDataSourceFake : ChannelTypeDataSource {
    private var channelList: MutableList<ChannelType> = mutableListOf()
    override suspend fun saveChannelType(channelType: ChannelType) {
        channelList.add(channelType)
    }

    override suspend fun deleteChannelType(channelType: ChannelType) {
        channelList.remove(channelType)
    }

    override fun getChannelTypes(): Flow<List<ChannelType>> {
        return flowOf(channelList)
    }

    override fun getChannelType(channelTypeId: Long): Flow<ChannelType?> {
        return flowOf(channelList.find { it.id == channelTypeId })
    }
}