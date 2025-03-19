package com.e_conomic.invoice.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e_conomic.invoice.data.OnEvent
import com.e_conomic.invoice.data.Resource
import com.e_conomic.invoice.data.entities.InvoiceEntity
import com.e_conomic.invoice.usecases.DeleteInvoiceUseCase
import com.e_conomic.invoice.usecases.GetInvoicesUseCase
import com.e_conomic.invoice.usecases.SaveInvoiceUseCase
import com.e_conomic.invoice.usecases.UpdateInvoiceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getInvoicesUseCase: GetInvoicesUseCase,
    private val saveInvoiceUseCase: SaveInvoiceUseCase,
    private val updateInvoiceUseCase: UpdateInvoiceUseCase,
    private val deleteInvoiceUseCase: DeleteInvoiceUseCase,
) : ViewModel() {
    private val _getInvoiceFlow = MutableStateFlow<Resource<List<InvoiceEntity>>?>(null)
    val getInvoiceFlow: StateFlow<Resource<List<InvoiceEntity>>?> = _getInvoiceFlow

    private val _addInvoiceFlow = MutableStateFlow<Resource<Boolean>?>(null)
    val addInvoiceFlow: StateFlow<Resource<Boolean>?> = _addInvoiceFlow

    fun onEvent(event: OnEvent) {
        when(event) {
            is OnEvent.AddInvoice -> addInvoice(event.invoice)
            is OnEvent.GetInvoices -> fetchInvoices()
            is OnEvent.DeleteInvoice -> deleteInvoice(event.invoice)
            is OnEvent.UpdateInvoice -> updateInvoice(event.invoice)
        }
    }

    init {
        fetchInvoices()
    }

    private fun fetchInvoices() {
        viewModelScope.launch {
            _getInvoiceFlow.value = Resource.Loading
            getInvoicesUseCase.execute(Unit)
                .collect { notes ->
                    _getInvoiceFlow.value =  Resource.Success(notes)
                }
        }
    }

    private fun addInvoice(invoice: InvoiceEntity) {
        _addInvoiceFlow.value = Resource.Loading
        viewModelScope.launch {
            try {
                saveInvoiceUseCase.execute(invoice)
                fetchInvoices()
                _addInvoiceFlow.value = Resource.Success(true)
            } catch (e: Exception) {
                _addInvoiceFlow.value = Resource.Failure(e)
            }
        }
    }

    private fun updateInvoice(invoice: InvoiceEntity) {
        _addInvoiceFlow.value = Resource.Loading
        viewModelScope.launch {
            try {
                updateInvoiceUseCase.execute(invoice)
                fetchInvoices()
                _addInvoiceFlow.value = Resource.Success(true)
            } catch (e: Exception) {
                _addInvoiceFlow.value = Resource.Failure(e)
            }
        }
    }

    private fun deleteInvoice(invoice: InvoiceEntity) {
        viewModelScope.launch {
            _addInvoiceFlow.value = Resource.Loading
            try {
                deleteInvoiceUseCase.execute(invoice)
                fetchInvoices()
                _addInvoiceFlow.value = Resource.Success(true)
            } catch (e: Exception) {
                _addInvoiceFlow.value = Resource.Failure(e)
            }
        }
    }
}