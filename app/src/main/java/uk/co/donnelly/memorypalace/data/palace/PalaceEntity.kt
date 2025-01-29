package uk.co.donnelly.memorypalace.data.palace

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "palace")
data class PalaceEntity(
    val name: String,
    val type: String,
    val imageUrl: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}