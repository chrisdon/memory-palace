package uk.co.donnelly.memorypalace.ui.palace

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.co.donnelly.memorypalace.domain.entities.Station
import uk.co.donnelly.memorypalace.ui.station.StationDisplay

@Composable
fun StationItemCard(
    stationDisplay: StationDisplay,
    onClick: (Station) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = stationDisplay.station.name, fontSize = 24.sp, modifier = Modifier
            .clickable { onClick(stationDisplay.station) })
        Spacer(Modifier.weight(1f))
        Card(
            colors = CardDefaults.cardColors().copy(containerColor = Color(stationDisplay.channel.colour.toULong()))
        ) {
            Text(
                text = stationDisplay.channel.name[0].toString(),
                fontSize = 32.sp,
                modifier = Modifier.padding(16.dp),
                color = Color.White
            )
        }
    }
}