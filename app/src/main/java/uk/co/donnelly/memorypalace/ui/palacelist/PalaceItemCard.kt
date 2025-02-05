package uk.co.donnelly.memorypalace.ui.palacelist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.co.donnelly.memorypalace.domain.entities.Palace

@Composable
fun PalaceItemCard(
    palaceItem: Palace,
    onClick: (Palace) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = palaceItem.name, fontSize = 20.sp, modifier = Modifier.fillMaxWidth().padding(16.dp).clickable { onClick(palaceItem) })
    }
}