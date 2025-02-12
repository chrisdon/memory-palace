package uk.co.donnelly.memorypalace.ui.palacelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import uk.co.donnelly.memorypalace.domain.entities.Palace
import uk.co.donnelly.memorypalace.domain.usecases.GetPalacesUseCase
import javax.inject.Inject

@HiltViewModel
class PalaceListViewModel @Inject constructor(
    getPalacesUseCase: GetPalacesUseCase,
) : ViewModel() {
    val palacesFlow = getPalacesUseCase.invoke().map { palaces ->
        PalaceListUiState.Success(palaces)
    }
        .catch {
            PalaceListUiState.Error(it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = PalaceListUiState.Loading
        )
}

sealed class PalaceListUiState {
    data object Loading : PalaceListUiState()
    data class Success(val palaces: List<Palace>) : PalaceListUiState()
    data class Error(val t: Throwable) : PalaceListUiState()
}