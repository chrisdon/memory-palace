package uk.co.donnelly.memorypalace.data.station

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface StationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveStation(station: StationEntity) : Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveStation(stations: List<StationEntity>) : List<Long>

    @Update
    suspend fun updateStation(station: StationEntity)

    @Update
    suspend fun updateStation(stations: List<StationEntity>)

    @Query("SELECT * FROM station WHERE palaceId = :palaceId")
    fun getSavedStations(palaceId: Long) : Flow<List<StationEntity>>

    @Query("SELECT * FROM station WHERE id = :stationId")
    fun getSavedStation(stationId: Long): Flow<StationEntity?>

    @Delete
    suspend fun deleteStation(station: StationEntity)

    @Transaction
    suspend fun insertOrUpdate(station: StationEntity) {
        val id = saveStation(station)
        if (id == -1L) updateStation(station)
    }

    @Transaction
    suspend fun insertOrUpdate(stations: List<StationEntity>) {
        val insertResult = saveStation(stations)
        val updateList = mutableListOf<StationEntity>()

        for (i in insertResult.indices) {
            if (insertResult[i] == -1L) updateList.add(stations[i])
        }

        if (updateList.isNotEmpty()) updateStation(updateList)
    }
}