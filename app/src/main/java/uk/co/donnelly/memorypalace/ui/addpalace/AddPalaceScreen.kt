package uk.co.donnelly.memorypalace.ui.addpalace

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import uk.co.donnelly.memorypalace.R
import uk.co.donnelly.memorypalace.domain.entities.Palace
import uk.co.donnelly.memorypalace.ui.common.ImageDisplay
import uk.co.donnelly.memorypalace.ui.common.PalaceInputText
import uk.co.donnelly.memorypalace.ui.theme.PalaceTopAppBarColors

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddPalaceScreen(
    onAddPalace: (Palace) -> Unit = {}
) {
    val (name, onNameChange) = rememberSaveable { mutableStateOf("") }
    val (imagePath, setImagePath) = rememberSaveable { mutableStateOf("") }

    Scaffold(
        content = { paddingValues ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                PalaceInputText(
                    text = name,
                    label = stringResource(id = R.string.add_palace_name),
                    onTextChange = onNameChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 0.dp, vertical = 16.dp)
                        .testTag(TAG_INPUT_NAME)
                )
                ImageDisplay(
                    onImageUri = { uri ->
                        uri?.toString()?.let { setImagePath(it) }
                    }
                )
            }
        },
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.add_palace)) },
                actions = {
                    IconButton(onClick = {
                        if(name.isNotEmpty()) {
                            onAddPalace(
                                Palace(
                                    id = -1,
                                    name = name,
                                    type = "",
                                    imageUrl = imagePath
                                )
                            )
                        }
                    }) {
                        Icon(
                            contentDescription = stringResource(R.string.save_palace_content_desc),
                            imageVector = Icons.Default.Save,
                            tint = Color.White
                        )
                    }

                },
                colors = PalaceTopAppBarColors()
            )
        }
    )
}

const val TAG_INPUT_NAME = "inputName"