package uk.co.donnelly.memorypalace.data.channeltype

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
    override suspend fun saveChannelType(channelType: ChannelType) {
        channelTypeDao.insertOrUpdate(channelTypeEntityMapper.toChannelTypeEntity(channelType))
    }

    override suspend fun deleteChannelType(channelType: ChannelType) {
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