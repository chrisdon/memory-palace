package uk.co.donnelly.memorypalace.data.channeltype

import uk.co.donnelly.memorypalace.domain.entities.ChannelType

interface ChannelTypeMapper {
    fun toChannelTypeEntity(channelType: ChannelType) : ChannelTypeEntity
    fun toChannelType(channelTypeEntity: ChannelTypeEntity) : ChannelType
}

class ChannelTypeMapperImpl : ChannelTypeMapper {
    override fun toChannelTypeEntity(channelType: ChannelType): ChannelTypeEntity {
        return if(channelType.id <= 0) {
            toChannelTypeEntityNoId(channelType)
        } else {
            toChannelTypeEntityWithId(channelType)
        }
    }

    override fun toChannelType(channelTypeEntity: ChannelTypeEntity): ChannelType {
        return ChannelType(
            id = channelTypeEntity.id,
            name = channelTypeEntity.name,
            colour = channelTypeEntity.colour
        )
    }

    private fun toChannelTypeEntityNoId(channelType: ChannelType): ChannelTypeEntity {
        return ChannelTypeEntity(
            name = channelType.name,
            colour = channelType.colour
        )
    }

    private fun toChannelTypeEntityWithId(channelType: ChannelType): ChannelTypeEntity {
        val channelTypeEntity = ChannelTypeEntity(
            name = channelType.name,
            colour = channelType.colour
        )
        channelTypeEntity.id = channelType.id
        return channelTypeEntity
    }
}