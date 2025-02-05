package uk.co.donnelly.memorypalace.ui.palacelist

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.co.donnelly.memorypalace.R
import uk.co.donnelly.memorypalace.domain.entities.Palace
import uk.co.donnelly.memorypalace.ui.common.Loading
import uk.co.donnelly.memorypalace.ui.theme.MemoryPalaceTheme

@Composable
fun PalaceListScreen(
    palaceState: PalaceListUiState,
    onNewPalace: () -> Unit = {},
    onPalace: (Palace) -> Unit = {}
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onNewPalace()
                }
            ) {
                Icon(Icons.Filled.Add, stringResource(id = R.string.add_palace_fab))
            }
        },
        content = { padding ->
            when (palaceState) {
                is PalaceListUiState.Loading -> {
                    Loading()
                }
                is PalaceListUiState.Success -> {
                    val palaceList = palaceState.palaces
                    if (palaceList.isEmpty()) {
                        Surface(modifier = Modifier.padding(24.dp)) {
                            Text(
                                text = stringResource(id = R.string.no_palaces),
                                fontSize = 20.sp,
                            )
                        }
                    } else {
                        LazyColumn {
                            items(palaceList) { palace ->
                                PalaceItemCard(palaceItem = palace, onClick = { palaceItem ->
                                    onPalace(palaceItem)
                                })
                            }
                        }
                    }
                }
                is PalaceListUiState.Error -> {

                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PalaceListPreview() {
    MemoryPalaceTheme {
        PalaceListScreen(PalaceListUiState.Success(previewPalaces()))
    }
}

private fun previewPalaces(): List<Palace> {
    return listOf(
        Palace(id = 0, name = "Addiscombe", type = "", imageUrl = null),
        Palace(id = 1, name = "Burwell", type = "", imageUrl = null),
    )
}