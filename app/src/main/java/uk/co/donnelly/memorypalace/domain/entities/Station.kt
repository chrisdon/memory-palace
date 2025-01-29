package uk.co.donnelly.memorypalace.domain.entities

data class Station(
    val id: Long,
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
)