package uk.co.donnelly.memorypalace.data.channeltype

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import uk.co.donnelly.memorypalace.domain.entities.ChannelType
import uk.co.donnelly.memorypalace.domain.repositories.ChannelTypeRepository

class ChannelTypeRepositoryImpl(
    val channelTypeDataSource: ChannelTypeDataSource
) : ChannelTypeRepository {
    override fun getChannelTypes(): Flow<List<ChannelType>> {
        return channelTypeDataSource.getChannelTypes()
    }

    override suspend fun addChannelType(channelType: ChannelType) {
        channelTypeDataSource.saveChannelType(channelType)
    }

    override suspend fun removeChannelType(channelType: ChannelType) {
        channelTypeDataSource.deleteChannelType(channelType)
    }

    override fun getChannelType(channelTypeId: Long): Flow<ChannelType?> {
        return channelTypeDataSource.getChannelType(channelTypeId)
    }
}

class ChannelTypeRepositoryFake : ChannelTypeRepository {
    private val channels = mutableListOf<ChannelType>()

    override fun getChannelTypes(): Flow<List<ChannelType>> = flowOf(channels)

    override suspend fun addChannelType(channelType: ChannelType) {
        channels.add(channelType)
    }

    override suspend fun removeChannelType(channelType: ChannelType) {
        channels.remove(channelType)
    }

    override fun getChannelType(channelTypeId: Long): Flow<ChannelType?> {
        return flowOf(channels.find { it.id == channelTypeId })
    }
}