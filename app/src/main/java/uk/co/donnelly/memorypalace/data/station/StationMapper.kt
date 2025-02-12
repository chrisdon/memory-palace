package uk.co.donnelly.memorypalace.data.station

import uk.co.donnelly.memorypalace.domain.entities.ChannelType
import uk.co.donnelly.memorypalace.domain.entities.Station
import uk.co.donnelly.memorypalace.ui.station.StationDisplay

interface StationMapper {
    fun toStationEntity(station: Station) : StationEntity
    fun toStation(stationEntity: StationEntity) : Station
    fun toDisplayStations(stationList: List<Station>, channels: List<ChannelType>): List<StationDisplay>
}

class StationMapperImpl : StationMapper {
    override fun toStationEntity(station: Station): StationEntity {
        return if (station.id <= 0) {
            toStationEntityWithoutId(station)
        } else {
            toStationEntityWithId(station)
        }
    }

    override fun toStation(stationEntity: StationEntity): Station {
        return Station(
            id = stationEntity.id,
            palaceId = stationEntity.palaceId,
            name = stationEntity.name,
            order = stationEntity.order,
            word = stationEntity.word,
            wordGender = stationEntity.wordGender,
            image = stationEntity.image,
            action = stationEntity.action,
            meaning = stationEntity.meaning,
            meaningGender = stationEntity.meaningGender,
            phrase = stationEntity.phrase,
            phraseMeaning = stationEntity.phraseMeaning,
            channelTypeId = stationEntity.channelTypeId
        )
    }

    override fun toDisplayStations(
        stationList: List<Station>,
        channels: List<ChannelType>
    ): List<StationDisplay> {
        return stationList.map { station ->
            val stationItem = Station(
                id = station.id,
                palaceId = station.palaceId,
                name = station.name,
                order = station.order,
                word = station.word,
                wordGender = station.wordGender,
                image = station.image,
                action = station.action,
                meaning = station.meaning,
                meaningGender = station.meaningGender,
                phrase = station.phrase,
                phraseMeaning = station.phraseMeaning,
                channelTypeId = station.channelTypeId
            )
            val channelTypeItem = channels.first{it.id == station.channelTypeId}
            StationDisplay(station = stationItem, channel = channelTypeItem)
        }
    }

    private fun toStationEntityWithoutId(station: Station): StationEntity {
        return StationEntity(
            palaceId = station.palaceId,
            name = station.name,
            order = station.order,
            word = station.word,
            wordGender = station.wordGender,
            image = station.image,
            action = station.action,
            meaning = station.meaning,
            meaningGender = station.meaningGender,
            phrase = station.phrase,
            phraseMeaning = station.phraseMeaning,
            channelTypeId = station.channelTypeId
        )
    }

    private fun toStationEntityWithId(station: Station): StationEntity {
        val stationEntity = StationEntity(
            palaceId = station.palaceId,
            name = station.name,
            order = station.order,
            word = station.word,
            wordGender = station.wordGender,
            image = station.image,
            action = station.action,
            meaning = station.meaning,
            meaningGender = station.meaningGender,
            phrase = station.phrase,
            phraseMeaning = station.phraseMeaning,
            channelTypeId = station.channelTypeId
        )
        stationEntity.id = station.id
        return stationEntity
    }
}

