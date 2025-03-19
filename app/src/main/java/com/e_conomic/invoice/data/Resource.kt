package com.e_conomic.invoice.data

import com.e_conomic.invoice.data.entities.InvoiceEntity

sealed class Resource<out R> {
    data class Success<out R>(val result: R) : Resource<R>()
    data class Failure(val exception: Exception) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}

sealed class OnEvent {
    data class AddInvoice(val invoice: InvoiceEntity): OnEvent()
    data class UpdateInvoice(val invoice: InvoiceEntity): OnEvent()
    data object GetInvoices: OnEvent()
    data object ResetState: OnEvent()
    data class DeleteInvoice(val invoice: InvoiceEntity): OnEvent()
}