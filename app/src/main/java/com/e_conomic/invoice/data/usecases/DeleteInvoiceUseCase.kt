package com.e_conomic.invoice.data.usecases

import com.e_conomic.invoice.data.entities.InvoiceEntity
import com.e_conomic.invoice.data.repository.InvoiceRepository

class DeleteInvoiceUseCase(
    private val repository: InvoiceRepository
) : BaseUseCase<InvoiceEntity, Unit> {

    override suspend fun execute(input: InvoiceEntity) {
        try {
            repository.deleteInvoice(input)
        } catch (e: Exception) {
            throw e
        }
    }
}