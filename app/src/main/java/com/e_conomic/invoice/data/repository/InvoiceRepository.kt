package com.e_conomic.invoice.data.repository

import com.e_conomic.invoice.data.entities.InvoiceEntity
import kotlinx.coroutines.flow.Flow

interface InvoiceRepository {

    suspend fun deleteInvoice(invoice: InvoiceEntity)
    fun getInvoices(): Flow<List<InvoiceEntity>>
    suspend fun saveInvoice(invoice: InvoiceEntity)
    suspend fun updateInvoiceLocal(invoice: InvoiceEntity)
}
