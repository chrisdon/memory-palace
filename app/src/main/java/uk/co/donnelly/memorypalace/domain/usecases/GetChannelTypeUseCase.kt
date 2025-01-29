package uk.co.donnelly.memorypalace.domain.usecases

import uk.co.donnelly.memorypalace.domain.repositories.ChannelTypeRepository

class GetChannelTypeUseCase(private val channelTypeRepository: ChannelTypeRepository) {
    operator fun invoke(channelTypeId: Long) = channelTypeRepository.getChannelType(channelTypeId)
}