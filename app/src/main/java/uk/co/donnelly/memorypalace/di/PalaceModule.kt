package uk.co.donnelly.memorypalace.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import uk.co.donnelly.memorypalace.data.common.ImageUtil
import uk.co.donnelly.memorypalace.data.common.ImageUtilImpl
import uk.co.donnelly.memorypalace.data.palace.PalaceDao
import uk.co.donnelly.memorypalace.data.palace.PalaceDataSource
import uk.co.donnelly.memorypalace.data.palace.PalaceDataSourceImpl
import uk.co.donnelly.memorypalace.data.palace.PalaceMapper
import uk.co.donnelly.memorypalace.data.palace.PalaceMapperImpl
import uk.co.donnelly.memorypalace.data.palace.PalaceRepositoryImpl
import uk.co.donnelly.memorypalace.domain.repositories.PalaceRepository
import uk.co.donnelly.memorypalace.domain.usecases.AddPalaceUseCase
import uk.co.donnelly.memorypalace.domain.usecases.DeletePalaceUseCase
import uk.co.donnelly.memorypalace.domain.usecases.GetPalaceUseCase
import uk.co.donnelly.memorypalace.domain.usecases.GetPalacesUseCase
import uk.co.donnelly.memorypalace.domain.usecases.GetPalacesUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
object PalaceModule {
    @Provides
    @ViewModelScoped
    fun provideGetPalacesUseCase(palaceRepository: PalaceRepository): GetPalacesUseCase {
        return GetPalacesUseCaseImpl(palaceRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideAddPalaceUseCase(palaceRepository: PalaceRepository): AddPalaceUseCase {
        return AddPalaceUseCase(palaceRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetPalaceUseCase(palaceRepository: PalaceRepository): GetPalaceUseCase {
        return GetPalaceUseCase(palaceRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideDeletePalaceUseCase(palaceRepository: PalaceRepository): DeletePalaceUseCase {
        return DeletePalaceUseCase(palaceRepository)
    }

    @Provides
    @ViewModelScoped
    fun providePalaceMapper(): PalaceMapper {
        return PalaceMapperImpl()
    }

    @Provides
    @ViewModelScoped
    fun provideImageUtil(
        @ApplicationContext context: Context
    ): ImageUtil {
        return ImageUtilImpl(context)
    }

    @Provides
    @ViewModelScoped
    fun providePalaceDataSource(
        palaceDao: PalaceDao,
        palaceMapper: PalaceMapper,
        imageUtil: ImageUtil
    ): PalaceDataSource {
        return PalaceDataSourceImpl(palaceDao, Dispatchers.IO, palaceMapper, imageUtil)
    }

    @Provides
    @ViewModelScoped
    fun providePalaceRepository(palaceDataSource: PalaceDataSource): PalaceRepository {
        return PalaceRepositoryImpl(palaceDataSource)
    }


}