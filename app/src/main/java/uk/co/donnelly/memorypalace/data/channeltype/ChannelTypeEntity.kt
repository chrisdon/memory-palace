package uk.co.donnelly.memorypalace.data.channeltype

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ChannelType")
data class ChannelTypeEntity(

    val name: String,
    val colour: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}