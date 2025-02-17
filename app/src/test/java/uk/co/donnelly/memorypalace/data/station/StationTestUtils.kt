package uk.co.donnelly.memorypalace.data.station

import uk.co.donnelly.memorypalace.data.channeltype.channelTypeDomainGerman
import uk.co.donnelly.memorypalace.domain.entities.Station
import uk.co.donnelly.memorypalace.ui.station.StationDisplay

val stationEntity = StationEntity(
    name = "Front door",
    palaceId = 1L,
    order = 0,
    word = "Aiziet",
    wordGender = "n/a",
    image = "Eyes",
    action = "next to ear",
    meaning = "To depart",
    meaningGender = "n/a",
    phrase = "Es gribu aiziet",
    phraseMeaning = "I want to leave",
    channelTypeId = 1L
)
val station = Station(
    id = 0,
    name = "Front door",
    palaceId = 1L,
    order = 0,
    word = "Aiziet",
    wordGender = "n/a",
    image = "Eyes",
    action = "next to ear",
    meaning = "To depart",
    meaningGender = "n/a",
    phrase = "Es gribu aiziet",
    phraseMeaning = "I want to leave",
    channelTypeId = 1L
)
val stationEntity2 = StationEntity(
    name = "Hallway",
    palaceId = 1L,
    order = 1,
    word = "Avize",
    wordGender = "female",
    image = "Visa card",
    action = "Spinning",
    meaning = "Newspaper",
    meaningGender = "n/a",
    phrase = "Es lasu avizi",
    phraseMeaning = "I read a newspaper",
    channelTypeId = 1L
)
val station1 = Station(
    id = 1,
    name = "Porch",
    palaceId = 1L,
    order = 1,
    word = "Aploksne",
    wordGender = "female",
    image = "Visa card",
    action = "Spinning",
    meaning = "Envelope",
    meaningGender = "n/a",
    phrase = "Man vajaziga aploksne",
    phraseMeaning = "I need an envelope",
    channelTypeId = 1L
)
val station2 = Station(
    id = 2,
    name = "Hallway",
    palaceId = 1L,
    order = 1,
    word = "Avize",
    wordGender = "female",
    image = "Visa card",
    action = "Spinning",
    meaning = "Newspaper",
    meaningGender = "n/a",
    phrase = "Es lasu avizi",
    phraseMeaning = "I read a newspaper",
    channelTypeId = 1L
)
val station2id0 = Station(
    id = 0,
    name = "Hallway",
    palaceId = 1L,
    order = 1,
    word = "Avize",
    wordGender = "female",
    image = "Visa card",
    action = "Spinning",
    meaning = "Newspaper",
    meaningGender = "n/a",
    phrase = "Es lasu avizi",
    phraseMeaning = "I read a newspaper",
    channelTypeId = 1L
)
val station1Amended = StationEntity(
    name = "Front door",
    palaceId = 1L,
    order = 0,
    word = "Aiziet",
    wordGender = "n/a",
    image = "Eyes",
    action = "next to ear",
    meaning = "To leave",
    meaningGender = "n/a",
    phrase = "Es gribu aiziet",
    phraseMeaning = "I want to leave",
    channelTypeId = 1L
)
val station2Amended = StationEntity(
    name = "Hallway",
    palaceId = 1L,
    order = 1,
    word = "Avize",
    wordGender = "female",
    image = "Visa card",
    action = "Spinning",
    meaning = "Newspaper",
    meaningGender = "n/a",
    phrase = "Mes lasam avizi",
    phraseMeaning = "I read a newspaper",
    channelTypeId = 1L
)
val updatedStations = listOf(station1Amended, station2Amended).map {
    val station = it
    if(it.order == 0) {
        station.id = 1
    } else {
        station.id = 2
    }
    station
}
val stations = listOf(station1, station2)
val stationDisplays = listOf(
    StationDisplay(station1, channelTypeDomainGerman),
    StationDisplay(station2, channelTypeDomainGerman)
)

