package com.e_conomic.invoice.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e_conomic.invoice.data.Resource
import com.e_conomic.invoice.data.entities.InvoiceEntity
import com.e_conomic.invoice.usecases.GetInvoicesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getInvoicesUseCase: GetInvoicesUseCase,
) : ViewModel() {
    private val _addInvoiceFlow = MutableStateFlow<Resource<List<InvoiceEntity>>?>(null)
    val addInvoiceFlow: StateFlow<Resource<List<InvoiceEntity>>?> = _addInvoiceFlow


    init {
        fetchNotes()
    }

    fun fetchNotes() {
        viewModelScope.launch {
            _addInvoiceFlow.value = Resource.Loading
            getInvoicesUseCase.execute(Unit)
                .collect { notes ->
                    _addInvoiceFlow.value =  Resource.Success(notes)
                }
        }
    }
}