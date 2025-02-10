package uk.co.donnelly.memorypalace.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import uk.co.donnelly.memorypalace.data.db.PalaceDatabase
import uk.co.donnelly.memorypalace.data.palace.PalaceDao
import uk.co.donnelly.memorypalace.data.palace.PalaceDataSource
import uk.co.donnelly.memorypalace.data.palace.PalaceDataSourceImpl
import uk.co.donnelly.memorypalace.data.palace.PalaceMapperImpl
import uk.co.donnelly.memorypalace.data.palace.PalaceRepositoryImpl
import uk.co.donnelly.memorypalace.domain.repositories.PalaceRepository
import uk.co.donnelly.memorypalace.domain.usecases.AddPalaceUseCase
import uk.co.donnelly.memorypalace.domain.usecases.GetPalacesUseCase
import uk.co.donnelly.memorypalace.domain.usecases.GetPalacesUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PalaceListModule {
    @Provides
    @Singleton
    fun provideGetPalacesUseCase(palaceRepository: PalaceRepository): GetPalacesUseCase {
        return GetPalacesUseCaseImpl(palaceRepository)
    }

    @Provides
    @Singleton
    fun provideAddPalaceUseCase(palaceRepository: PalaceRepository): AddPalaceUseCase {
        return AddPalaceUseCase(palaceRepository)
    }

    @Provides
    @Singleton
    fun providePalaceRepository(palaceDataSource: PalaceDataSource): PalaceRepository {
        return PalaceRepositoryImpl(palaceDataSource)
    }

    @Provides
    @Singleton
    fun providePalaceDataSource(palaceDao: PalaceDao): PalaceDataSource {
        return PalaceDataSourceImpl(palaceDao, Dispatchers.IO, PalaceMapperImpl())
    }

    @Provides
    @Singleton
    fun providePalaceDao(db: PalaceDatabase): PalaceDao {
        return db.palaceDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): PalaceDatabase {
        return PalaceDatabase.getDatabase(appContext)
    }
}