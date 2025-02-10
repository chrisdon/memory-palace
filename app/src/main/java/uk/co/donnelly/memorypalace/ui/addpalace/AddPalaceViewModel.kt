package uk.co.donnelly.memorypalace.ui.addpalace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uk.co.donnelly.memorypalace.domain.entities.Palace
import uk.co.donnelly.memorypalace.domain.usecases.AddPalaceUseCase
import javax.inject.Inject

@HiltViewModel
class AddPalaceViewModel @Inject constructor(
    private val addPalaceUseCase: AddPalaceUseCase
) : ViewModel() {
    fun savePalace(palace: Palace) {
        viewModelScope.launch {
            addPalaceUseCase.invoke(palace)
        }
    }
}