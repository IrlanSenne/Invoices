package com.e_conomic.invoice

import com.e_conomic.invoice.data.entities.InvoiceEntity
import com.e_conomic.invoice.data.repository.InvoiceRepository
import com.e_conomic.invoice.data.usecases.DeleteInvoiceUseCase
import com.e_conomic.invoice.data.usecases.GetInvoicesUseCase
import com.e_conomic.invoice.data.usecases.SaveInvoiceUseCase
import com.e_conomic.invoice.data.usecases.UpdateInvoiceUseCase
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class InvoiceUseCaseTest {

    private lateinit var invoiceRepository: InvoiceRepository
    private lateinit var deleteInvoiceUseCase: DeleteInvoiceUseCase
    private lateinit var getInvoicesUseCase: GetInvoicesUseCase
    private lateinit var saveInvoiceUseCase: SaveInvoiceUseCase
    private lateinit var updateInvoiceUseCase: UpdateInvoiceUseCase

    @Before
    fun setUp() {
        invoiceRepository = mockk()
        deleteInvoiceUseCase = DeleteInvoiceUseCase(invoiceRepository)
        getInvoicesUseCase = GetInvoicesUseCase(invoiceRepository)
        saveInvoiceUseCase = SaveInvoiceUseCase(invoiceRepository)
        updateInvoiceUseCase = UpdateInvoiceUseCase(invoiceRepository)
    }

    @Test
    fun `deleteInvoiceUseCase_should_call_deleteInvoice_method_in_repository`() = runTest {
        val invoice = InvoiceEntity("1", "Invoice 1","7845465", 100.0f)

        coEvery { invoiceRepository.deleteInvoice(invoice) } just Runs

        deleteInvoiceUseCase.execute(invoice)

        coVerify { invoiceRepository.deleteInvoice(invoice) }
    }

    @Test
    fun `getInvoicesUseCase_should_return_a_flow_of_invoices_from_repository`() = runTest {
        val invoices = listOf(
            InvoiceEntity("1", "Invoice 1", "3123221", 100.0f),
            InvoiceEntity("2", "Invoice 2", "323223332",200.0f)
        )
        coEvery { invoiceRepository.getInvoices() } returns flowOf(invoices)

        val result = getInvoicesUseCase.execute(Unit).first()

        assertEquals(invoices, result)
    }

    @Test
    fun `saveInvoiceUseCase_should_call_saveInvoice_method_in_repository`() = runTest {
        val invoice = InvoiceEntity("1", "Invoice 1","342343", 100.0f)

        coEvery { invoiceRepository.saveInvoice(invoice) } just Runs

        saveInvoiceUseCase.execute(invoice)

        coVerify { invoiceRepository.saveInvoice(invoice) }
    }

    @Test
    fun `updateInvoiceUseCase_should_call_updateInvoiceLocal_method_in_repository`() = runTest {
        val invoice = InvoiceEntity("1", "Invoice 1","43434", 100.0f)

        coEvery { invoiceRepository.updateInvoiceLocal(invoice) } just Runs

        updateInvoiceUseCase.execute(invoice)

        coVerify { invoiceRepository.updateInvoiceLocal(invoice) }
    }
}
