package uk.co.donnelly.memorypalace.data.palace

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PalaceDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun savePalace(palace: PalaceEntity) : Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun savePalace(palaces: List<PalaceEntity>) : List<Long>

    @Update
    suspend fun updatePalace(palace: PalaceEntity)

    @Update
    suspend fun updatePalace(palaces: List<PalaceEntity>)

    @Query("SELECT * FROM palace")
    fun getSavedPalaces(): Flow<List<PalaceEntity>>

    @Query("SELECT * FROM palace WHERE id = :palaceId")
    fun getSavedPalace(palaceId: Long): Flow<PalaceEntity?>

    @Delete
    suspend fun deletePalace(palace: PalaceEntity)

    @Transaction
    suspend fun insertOrUpdate(palace: PalaceEntity) {
        val id = savePalace(palace)
        if (id == -1L) updatePalace(palace)
    }

    @Transaction
    suspend fun insertOrUpdate(palaces: List<PalaceEntity>) {
        val insertResult = savePalace(palaces)
        val updateList = mutableListOf<PalaceEntity>()

        for (i in insertResult.indices) {
            if (insertResult[i] == -1L) updateList.add(palaces[i])
        }

        if (updateList.isNotEmpty()) updatePalace(updateList)
    }
}