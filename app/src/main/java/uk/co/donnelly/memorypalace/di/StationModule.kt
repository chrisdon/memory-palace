package uk.co.donnelly.memorypalace.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import uk.co.donnelly.memorypalace.data.palace.PalaceDataSource
import uk.co.donnelly.memorypalace.data.palace.PalaceRepositoryImpl
import uk.co.donnelly.memorypalace.data.station.StationDao
import uk.co.donnelly.memorypalace.data.station.StationDataSource
import uk.co.donnelly.memorypalace.data.station.StationDataSourceImpl
import uk.co.donnelly.memorypalace.data.station.StationMapper
import uk.co.donnelly.memorypalace.data.station.StationMapperImpl
import uk.co.donnelly.memorypalace.data.station.StationRepositoryImpl
import uk.co.donnelly.memorypalace.domain.repositories.PalaceRepository
import uk.co.donnelly.memorypalace.domain.repositories.StationRepository
import uk.co.donnelly.memorypalace.domain.usecases.AddPalaceUseCase
import uk.co.donnelly.memorypalace.domain.usecases.GetPalaceUseCase
import uk.co.donnelly.memorypalace.domain.usecases.GetPalacesUseCase
import uk.co.donnelly.memorypalace.domain.usecases.GetPalacesUseCaseImpl
import uk.co.donnelly.memorypalace.domain.usecases.GetStationsUseCase

@Module
@InstallIn(ViewModelComponent::class)
object StationModule {
    @Provides
    @ViewModelScoped
    fun provideStationDataSource(stationDao: StationDao, stationMapper: StationMapper): StationDataSource {
        return StationDataSourceImpl(stationDao, Dispatchers.IO, stationMapper)
    }

    @Provides
    @ViewModelScoped
    fun provideStationRepository(stationDataSource: StationDataSource): StationRepository {
        return StationRepositoryImpl(stationDataSource)
    }

    @Provides
    @ViewModelScoped
    fun provideGetStationsUseCase(stationRepository: StationRepository): GetStationsUseCase {
        return GetStationsUseCase(stationRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideStationMapper(): StationMapper {
        return StationMapperImpl()
    }
}