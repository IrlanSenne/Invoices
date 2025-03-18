package com.e_conomic.invoice.usecases

import com.e_conomic.invoice.data.entities.InvoiceEntity
import com.e_conomic.invoice.data.repository.InvoiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class GetInvoicesUseCase(
    private val invoiceRepository: InvoiceRepository
) : BaseUseCase<Unit, Flow<List<InvoiceEntity>>> {

    override suspend fun execute(input: Unit): Flow<List<InvoiceEntity>> {
        return invoiceRepository.getInvoices()
            .catch { emit(emptyList()) }
    }
}