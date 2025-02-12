package uk.co.donnelly.memorypalace.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import uk.co.donnelly.memorypalace.data.channeltype.ChannelTypeDao
import uk.co.donnelly.memorypalace.data.channeltype.ChannelTypeDataSource
import uk.co.donnelly.memorypalace.data.channeltype.ChannelTypeDataSourceImpl
import uk.co.donnelly.memorypalace.data.channeltype.ChannelTypeMapperImpl
import uk.co.donnelly.memorypalace.data.channeltype.ChannelTypeRepositoryImpl
import uk.co.donnelly.memorypalace.data.palace.PalaceDataSource
import uk.co.donnelly.memorypalace.data.palace.PalaceRepositoryImpl
import uk.co.donnelly.memorypalace.data.station.StationDao
import uk.co.donnelly.memorypalace.data.station.StationDataSource
import uk.co.donnelly.memorypalace.data.station.StationDataSourceImpl
import uk.co.donnelly.memorypalace.data.station.StationMapperImpl
import uk.co.donnelly.memorypalace.data.station.StationRepositoryImpl
import uk.co.donnelly.memorypalace.domain.repositories.ChannelTypeRepository
import uk.co.donnelly.memorypalace.domain.repositories.PalaceRepository
import uk.co.donnelly.memorypalace.domain.repositories.StationRepository
import uk.co.donnelly.memorypalace.domain.usecases.AddPalaceUseCase
import uk.co.donnelly.memorypalace.domain.usecases.GetChannelTypesUseCase
import uk.co.donnelly.memorypalace.domain.usecases.GetPalaceUseCase
import uk.co.donnelly.memorypalace.domain.usecases.GetPalacesUseCase
import uk.co.donnelly.memorypalace.domain.usecases.GetPalacesUseCaseImpl
import uk.co.donnelly.memorypalace.domain.usecases.GetStationsUseCase

@Module
@InstallIn(ViewModelComponent::class)
object ChannelTypeModule {
    @Provides
    @ViewModelScoped
    fun provideChannelTypeDataSource(channelTypeDao: ChannelTypeDao): ChannelTypeDataSource {
        return ChannelTypeDataSourceImpl(channelTypeDao, Dispatchers.IO, ChannelTypeMapperImpl())
    }

    @Provides
    @ViewModelScoped
    fun provideChannelTypeRepository(channelTypeDataSource: ChannelTypeDataSource):
            ChannelTypeRepository {
        return ChannelTypeRepositoryImpl(channelTypeDataSource)
    }

    @Provides
    @ViewModelScoped
    fun provideGetChannelTypesUseCase(channelTypeRepository: ChannelTypeRepository):
            GetChannelTypesUseCase {
        return GetChannelTypesUseCase(channelTypeRepository)
    }
}