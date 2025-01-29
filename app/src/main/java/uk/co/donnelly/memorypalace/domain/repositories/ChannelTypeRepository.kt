package uk.co.donnelly.memorypalace.domain.repositories

import kotlinx.coroutines.flow.Flow
import uk.co.donnelly.memorypalace.domain.entities.ChannelType

interface ChannelTypeRepository {
    fun getChannelTypes(): Flow<List<ChannelType>>
    suspend fun addChannelType(channelType: ChannelType)
    suspend fun removeChannelType(channelType: ChannelType)
    fun getChannelType(channelTypeId: Long): Flow<ChannelType?>
}