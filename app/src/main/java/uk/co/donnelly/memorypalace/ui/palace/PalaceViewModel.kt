package uk.co.donnelly.memorypalace.ui.palace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import uk.co.donnelly.memorypalace.data.station.StationMapper
import uk.co.donnelly.memorypalace.domain.entities.Palace
import uk.co.donnelly.memorypalace.domain.usecases.AddPalaceUseCase
import uk.co.donnelly.memorypalace.domain.usecases.DeletePalaceUseCase
import uk.co.donnelly.memorypalace.domain.usecases.GetChannelTypesUseCase
import uk.co.donnelly.memorypalace.domain.usecases.GetPalaceUseCase
import uk.co.donnelly.memorypalace.domain.usecases.GetStationsUseCase
import uk.co.donnelly.memorypalace.ui.station.StationDisplay
import javax.inject.Inject

@HiltViewModel
class PalaceViewModel @Inject constructor(
    private val addPalaceUseCase: AddPalaceUseCase,
    private val getPalaceUseCase: GetPalaceUseCase,
    private val getStationsUseCase: GetStationsUseCase,
    private val deletePalaceUseCase: DeletePalaceUseCase,
    private val getChannelTypesUseCase: GetChannelTypesUseCase,
    private val stationMapper: StationMapper
) : ViewModel() {
    private val _uiState = MutableStateFlow<PalaceUiState>(PalaceUiState.Loading)
    val uiState: StateFlow<PalaceUiState> = _uiState

    fun getPalace(palaceId: Long) {
        viewModelScope.launch {
            val combined = combine(
                getPalaceUseCase(palaceId = palaceId),
                getStationsUseCase(palaceId = palaceId),
                getChannelTypesUseCase()
            ) { palace, stations, channels ->
                Triple(palace, stations, channels)
            }
            combined.collect { (palace, stations, channels) ->
                if (palace != null) {
                    _uiState.value = PalaceUiState.Success(
                        palace,
                        stationMapper.toDisplayStations(stations, channels)
                    )
                } else {
                    _uiState.value =
                        PalaceUiState.Error(Exception("No value found for palace id: $palaceId"))
                }

            }
        }
    }

    fun savePalace(palace: Palace) {
        viewModelScope.launch {
            addPalaceUseCase.invoke(palace)
        }
    }

    fun deletePalace(palace: Palace) {
        viewModelScope.launch {
            deletePalaceUseCase.invoke(palace)
        }
    }
}

sealed class PalaceUiState {
    object Loading : PalaceUiState()
    data class Success(val palace: Palace, val stations: List<StationDisplay>) :
        PalaceUiState()

    data class Error(val e: Exception) : PalaceUiState() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Error

            return e.message == other.e.message
        }

        override fun hashCode(): Int {
            return e.hashCode()
        }
    }
}