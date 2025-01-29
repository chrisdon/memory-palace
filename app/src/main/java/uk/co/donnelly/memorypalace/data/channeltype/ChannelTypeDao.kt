package uk.co.donnelly.memorypalace.data.channeltype

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ChannelTypeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveChannelType(channelType: ChannelTypeEntity) : Long

    @Update
    suspend fun updateChannelType(channelType: ChannelTypeEntity)

    @Query("SELECT * FROM ChannelType")
    fun getSavedChannelTypes() : Flow<List<ChannelTypeEntity>>

    @Query("SELECT * FROM ChannelType where id=:channelTypeId")
    fun getSavedChannelType(channelTypeId : Long) : Flow<ChannelTypeEntity>

    @Delete
    suspend fun deleteChannelType(channelType: ChannelTypeEntity)

    @Transaction
    suspend fun insertOrUpdate(channelType: ChannelTypeEntity) {
        val id = saveChannelType(channelType)
        if (id == -1L) updateChannelType(channelType)
    }
}