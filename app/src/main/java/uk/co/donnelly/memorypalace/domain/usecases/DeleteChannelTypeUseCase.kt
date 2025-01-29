package uk.co.donnelly.memorypalace.domain.usecases

import uk.co.donnelly.memorypalace.domain.entities.ChannelType
import uk.co.donnelly.memorypalace.domain.repositories.ChannelTypeRepository

class DeleteChannelTypeUseCase(private val channelTypeRepository: ChannelTypeRepository) {
    suspend operator fun invoke(channelType: ChannelType) = channelTypeRepository.removeChannelType(channelType)
}