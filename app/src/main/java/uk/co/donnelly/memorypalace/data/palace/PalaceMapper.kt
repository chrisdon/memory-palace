package uk.co.donnelly.memorypalace.data.palace

import uk.co.donnelly.memorypalace.domain.entities.Palace

interface PalaceMapper {
    fun toPalaceEntity(palace: Palace): PalaceEntity
    fun toPalace(palaceEntity: PalaceEntity): Palace
}

class PalaceMapperImpl: PalaceMapper {

    override fun toPalaceEntity(palace: Palace): PalaceEntity {
        return if (palace.id <= 0) {
            toPalaceEntityWithoutId(palace)
        } else {
            toPalaceEntityWithId(palace)
        }
    }

    private fun toPalaceEntityWithoutId(palace: Palace): PalaceEntity {
        return PalaceEntity(
            name = palace.name,
            type = palace.type,
            imageUrl = palace.imageUrl
        )
    }

    private fun toPalaceEntityWithId(palace: Palace): PalaceEntity {
        val palaceEntity = PalaceEntity(
            name = palace.name,
            type = palace.type,
            imageUrl = palace.imageUrl
        )
        palaceEntity.id = palace.id
        return palaceEntity
    }

    override fun toPalace(palaceEntity: PalaceEntity): Palace {
        return Palace(
            id = palaceEntity.id,
            name = palaceEntity.name,
            type = palaceEntity.type,
            imageUrl = palaceEntity.imageUrl
        )
    }

}