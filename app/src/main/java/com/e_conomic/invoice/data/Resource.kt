package com.e_conomic.invoice.data

sealed class Resource<out R> {
    data class Success<out R>(val result: R) : Resource<R>()
    data class Failure(val exception: Exception) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}

sealed class OnEvent {
    object AddInvoice: OnEvent()
/*    data class Invoices(val notes: List<InvoiceEntity>): OnEvent()*/
}