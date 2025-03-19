package com.e_conomic.invoice.data.repository

import com.e_conomic.invoice.data.entities.InvoiceEntity
import com.e_conomic.invoice.data.localdatabase.InvoicesDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InvoiceRepositoryImpl @Inject constructor(
    private val invoiceDao: InvoicesDao,
) : InvoiceRepository {
    override suspend fun deleteInvoice(invoice: InvoiceEntity) {
        return try {
            invoiceDao.deleteNote(invoice)
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getInvoices(): Flow<List<InvoiceEntity>> {
        return try {
            invoiceDao.getAllNotes()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun saveInvoice(invoice: InvoiceEntity) {
        invoiceDao.insertInvoice(invoice)
    }

    override suspend fun updateInvoiceLocal(invoice: InvoiceEntity) {
        invoiceDao.updateNote(invoice)
    }
}