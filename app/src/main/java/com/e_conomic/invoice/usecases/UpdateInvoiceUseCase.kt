package com.e_conomic.invoice.usecases

import com.e_conomic.invoice.data.entities.InvoiceEntity
import com.e_conomic.invoice.data.repository.InvoiceRepository

class UpdateInvoiceUseCase(
    private val repository: InvoiceRepository
) : BaseUseCase<InvoiceEntity, Unit> {

    override suspend fun execute(input: InvoiceEntity) {
        try {
            repository.updateInvoiceLocal(input)
        } catch (e: Exception) {
            throw e
        }
    }
}