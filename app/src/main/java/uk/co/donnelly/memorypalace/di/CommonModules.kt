package uk.co.donnelly.memorypalace.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uk.co.donnelly.memorypalace.data.channeltype.ChannelTypeDao
import uk.co.donnelly.memorypalace.data.db.PalaceDatabase
import uk.co.donnelly.memorypalace.data.palace.PalaceDao
import uk.co.donnelly.memorypalace.data.station.StationDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModules {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): PalaceDatabase {
        return PalaceDatabase.getDatabase(appContext)
    }

    @Provides
    @Singleton
    fun providePalaceDao(db: PalaceDatabase): PalaceDao {
        return db.palaceDao()
    }

    @Provides
    @Singleton
    fun provideStationDao(db: PalaceDatabase): StationDao {
        return db.stationDao()
    }

    @Provides
    @Singleton
    fun provideChannelTypeDao(db: PalaceDatabase): ChannelTypeDao {
        return db.channelTypeDao()
    }
}