package uk.co.donnelly.memorypalace.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import uk.co.donnelly.memorypalace.data.channeltype.ChannelTypeDao
import uk.co.donnelly.memorypalace.data.channeltype.ChannelTypeEntity
import uk.co.donnelly.memorypalace.data.palace.PalaceDao
import uk.co.donnelly.memorypalace.data.palace.PalaceEntity
import uk.co.donnelly.memorypalace.data.station.StationDao
import uk.co.donnelly.memorypalace.data.station.StationEntity

@Database(entities = [PalaceEntity::class, StationEntity::class, ChannelTypeEntity::class],
    version = 1, exportSchema = false)
abstract class PalaceDatabase: RoomDatabase() {
    abstract fun palaceDao() : PalaceDao
    abstract fun stationDao() : StationDao
    abstract fun channelTypeDao() : ChannelTypeDao

    companion object {
        @Volatile
        private var INSTANCE : PalaceDatabase? = null

        fun getDatabase(appContext: Context) : PalaceDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    appContext, PalaceDatabase::class.java,
                    "palace.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}