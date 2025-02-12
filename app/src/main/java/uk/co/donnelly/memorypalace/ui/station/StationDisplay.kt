package uk.co.donnelly.memorypalace.ui.station

import uk.co.donnelly.memorypalace.domain.entities.ChannelType
import uk.co.donnelly.memorypalace.domain.entities.Station

data class StationDisplay(val station: Station, val channel: ChannelType)