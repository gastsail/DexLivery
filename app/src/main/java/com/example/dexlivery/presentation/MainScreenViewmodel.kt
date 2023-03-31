package com.example.dexlivery.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dexlivery.data.model.Product
import com.example.dexlivery.domain.MainRepo
import com.example.dexlivery.screens.MainScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewmodel @Inject constructor(
    private val mainScreenRepo: MainRepo
) : ViewModel() {

    private val mainScreenState = MutableStateFlow(MainScreenViewModelState())
    val uiState = mainScreenState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            mainScreenState.value.toUiState()
        )

    init {
        fetchProductList()
    }

    private fun fetchProductList() {
        mainScreenState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            delay(4000)
            try {
                val productList = mainScreenRepo.fetchProductList()
                mainScreenState.update {
                    it.copy(
                        isLoading = false,
                        productList = productList
                    )
                }
            } catch (e: java.lang.Exception) {
                mainScreenState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }

}

data class MainScreenViewModelState(
    val isLoading: Boolean = false,
    val productList: List<Product> = emptyList(),
    val selectedProductList: List<Product> = emptyList(),
    val error: String? = null
) {
    fun toUiState(): MainScreenState {
        if (isLoading) return MainScreenState.Loading
        if (error != null) return MainScreenState.Error(errorMsg = error)
        return if (productList.isNotEmpty()) MainScreenState.MainScreenStateUi(
            MainScreenUiState(
                productList = productList,
                selectedProducts = selectedProductList,
                checkoutButtonEnabled = selectedProductList.isNotEmpty()
            )
        ) else {
            MainScreenState.MainScreenStateUi(null)
        }
    }
}

sealed class MainScreenState {
    object Loading : MainScreenState()
    data class MainScreenStateUi(val mainScreenUiState: MainScreenUiState?) : MainScreenState()
    data class Error(val errorMsg: String) : MainScreenState()
}