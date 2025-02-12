package uk.co.donnelly.memorypalace.ui.palace

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import uk.co.donnelly.memorypalace.R
import uk.co.donnelly.memorypalace.domain.entities.Palace
import uk.co.donnelly.memorypalace.ui.common.AreYouSure
import uk.co.donnelly.memorypalace.ui.common.ErrorScreen
import uk.co.donnelly.memorypalace.ui.common.ImageDisplay
import uk.co.donnelly.memorypalace.ui.common.Loading
import uk.co.donnelly.memorypalace.ui.common.PalaceInputText
import uk.co.donnelly.memorypalace.ui.station.StationDisplay
import uk.co.donnelly.memorypalace.ui.theme.PalaceTopAppBarColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PalaceScreen(
    palaceState: PalaceUiState,
    onSavePalace: (Palace) -> Unit = {},
    onDeletePalace: (Palace) -> Unit = {},
    onAddStation: (Long) -> Unit = {},
    onDisplayStation: (Palace, StationDisplay) -> Unit = { _, _ -> }
) {
    when (palaceState) {
        PalaceUiState.Loading -> {
            Scaffold(
                content = { paddingValues ->
                    Loading(modifier = Modifier.padding(paddingValues))
                },
                topBar = {
                    TopAppBar(
                        title = { Text(text = stringResource(id = R.string.title_loading)) },
                        colors = PalaceTopAppBarColors()
                    )
                }
            )
        }

        is PalaceUiState.Success -> {
            val palace = palaceState.palace
            val stations = palaceState.stations

            var isDisplayMode by remember { mutableStateOf(true) }
            val displayDeletePopup = remember { mutableStateOf(false) }
            val (name, onNameChange) = rememberSaveable { mutableStateOf(palace.name) }
            val (imagePath, setImagePath) = rememberSaveable { mutableStateOf(palace.imageUrl) }

            val deletePopupTitleText = stringResource(id = R.string.delete_popup_title)
            val deletePopupBodyText = stringResource(id = R.string.delete_popup_text)
            val deletePopupCancelButton =
                stringResource(id = R.string.delete_popup_negative_button_text)
            val deletePopupOkButton =
                stringResource(id = R.string.delete_popup_positive_button_text)

            Scaffold(
                floatingActionButton = {
                    if (isDisplayMode) {
                        FloatingActionButton(onClick = { onAddStation(palace.id) }) {
                            Icon(Icons.Filled.Add, stringResource(id = R.string.add_station_fab))
                        }
                    }
                },
                content = { padding ->
                    if (displayDeletePopup.value) {
                        AreYouSure(
                            titleText = deletePopupTitleText,
                            bodyText = deletePopupBodyText,
                            negativeButtonText = deletePopupCancelButton,
                            positiveButtonText = deletePopupOkButton,
                            action = {
                                onDeletePalace(palace)
                                displayDeletePopup.value = false
                            },
                            displayState = displayDeletePopup
                        )
                    } else {
                        if (isDisplayMode) {
                            Log.d("PalaceScreen", palace.toString())
                            PalaceDisplayMode(padding, palace, stations, onDisplayStation)
                        } else {
                            PalaceEditMode(
                                name,
                                onNameChange,
                                setImagePath
                            )
                        }
                    }
                },
                topBar = {
                    TopAppBar(
                        title = { Text(text = palace.name) },
                        actions = {
                            if (isDisplayMode) {
                                IconButton(onClick = { isDisplayMode = !isDisplayMode }) {
                                    Icon(
                                        contentDescription = stringResource(R.string.edit_palace_content_desc),
                                        imageVector = Icons.Default.Edit,
                                        tint = Color.White
                                    )
                                }
                                IconButton(onClick = { displayDeletePopup.value = true }) {
                                    Icon(
                                        contentDescription = stringResource(R.string.delete_palace_content_desc),
                                        imageVector = Icons.Default.Delete,
                                        tint = Color.White
                                    )
                                }
                            } else {
                                IconButton(onClick = {
                                    isDisplayMode = !isDisplayMode
                                    onSavePalace(
                                        Palace(
                                            id = palace.id,
                                            name = name,
                                            type = palace.type,
                                            imageUrl = imagePath
                                        )
                                    )
                                }) {
                                    Icon(
                                        contentDescription = stringResource(R.string.save_palace_content_desc),
                                        imageVector = Icons.Default.Save,
                                        tint = Color.White
                                    )
                                }
                            }
                        },
                        colors = PalaceTopAppBarColors()
                    )
                }
            )
        }

        is PalaceUiState.Error -> {
            Log.e("PalaceScreen", palaceState.e.toString())
            ErrorScreen()
        }
    }
}

@Composable
fun PalaceDisplayMode(
    paddingValues: PaddingValues,
    palace: Palace,
    stations: List<StationDisplay>,
    onDisplayStation: (Palace, StationDisplay) -> Unit = { _, _ -> }
) {
    Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
        palace.imageUrl?.let { imageUrl ->
            val painter = rememberAsyncImagePainter(
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(data = imageUrl)
                    .build()
            )
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 0.dp, max = 300.dp),
                painter = painter,
                contentDescription = "Captured image",
                contentScale = ContentScale.FillBounds
            )
        }
        if (stations.isEmpty()) {
            Box(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = stringResource(id = R.string.no_stations),
                    fontSize = 20.sp,
                )
            }
        } else {
            LazyColumn {
                items(stations) { station ->
                    StationItemCard(
                        stationDisplay = station,
                        onClick = { stationItem ->
                            onDisplayStation(palace, station)
                        })
                }
            }
        }

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PalaceEditMode(
    name: String,
    onNameChange: (String) -> Unit,
    setImagePath: (String?) -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ImageDisplay(
            onImageUri = { uri ->
                setImagePath(uri.toString())
            }
        )
        PalaceInputText(
            text = name,
            label = stringResource(id = R.string.add_palace_name),
            onTextChange = onNameChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp, vertical = 16.dp)
                .testTag(TAG_PALACE_INPUT_FIELD)
        )
    }
}

const val TAG_PALACE_INPUT_FIELD = "palaceInputField"

