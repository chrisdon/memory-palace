package uk.co.donnelly.memorypalace.data.channeltype

import uk.co.donnelly.memorypalace.domain.entities.ChannelType

val channelTypeDataFrench = ChannelTypeEntity(
    name = "French",
    colour = 1L
)
val channelTypeDataSpanish = ChannelTypeEntity(
    name = "Spanish",
    colour = 1L
)
val channelTypeDomainFrench = ChannelType(
    name = "French",
    colour = 1L,
    id = 0
)
val channelTypeDomainSpanish = ChannelType(
    name = "Spanish",
    colour = 1L,
    id = 2
)
val channelTypeDomainGerman = ChannelType(
    name = "German",
    colour = 1L,
    id = 1
)

val channels = listOf(channelTypeDomainFrench, channelTypeDomainSpanish, channelTypeDomainGerman)