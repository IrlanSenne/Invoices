package com.e_conomic.invoice.data.repository

import com.e_conomic.invoice.data.entities.InvoiceEntity
import com.e_conomic.invoice.data.localdatabase.InvoicesDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InvoiceRepositoryImpl @Inject constructor(
    private val invoiceDao: InvoicesDao,
) : InvoiceRepository {
    override suspend fun deleteInvoice(invoice: InvoiceEntity) {
        TODO("Not yet implemented")
    }

    override fun getInvoices(): Flow<List<InvoiceEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveInvoice(invoice: InvoiceEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun updateInvoiceLocal(invoice: InvoiceEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllInvoicesLocal() {
        TODO("Not yet implemented")
    }
}