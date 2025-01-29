package uk.co.donnelly.memorypalace.domain.usecases

import uk.co.donnelly.memorypalace.domain.repositories.ChannelTypeRepository

class GetChannelTypesUseCase(private val channelTypeRepository: ChannelTypeRepository) {
    operator fun invoke() = channelTypeRepository.getChannelTypes()
}