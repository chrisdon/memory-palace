package uk.co.donnelly.memorypalace.data.station

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import uk.co.donnelly.memorypalace.data.palace.PalaceEntity

@Entity(
    tableName = "station",
    foreignKeys = [ForeignKey(
        entity = PalaceEntity::class,
        parentColumns = ["id"],
        childColumns = ["palaceId"],
        onDelete = CASCADE
    )]
)
data class StationEntity(
    val palaceId: Long,
    val name: String,
    val order: Int,
    val word: String?,
    val wordGender: String,
    val image: String?,
    val action: String?,
    val meaning: String?,
    val meaningGender: String?,
    val phrase: String?,
    val phraseMeaning: String?,
    val channelTypeId: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}