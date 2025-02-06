package uk.co.donnelly.memorypalace.data.palace

import uk.co.donnelly.memorypalace.domain.entities.Palace

val palace1 = PalaceEntity(
    name = "Addiscombe",
    type = "",
    imageUrl = null
)

val palace1Amended = PalaceEntity(
    name = "Addington",
    type = "",
    imageUrl = null
)

val palaceDomainId0 = Palace(
    name = "Addiscombe",
    type = "",
    imageUrl = null,
    id = 0
)

val palaceDomainId1 = Palace(
    name = "Bermuda",
    type = "",
    imageUrl = null,
    id = 1
)

val palaceList = listOf(palaceDomainId0, palaceDomainId1)