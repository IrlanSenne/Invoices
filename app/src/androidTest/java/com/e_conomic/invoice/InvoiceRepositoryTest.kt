package com.e_conomic.invoice

import com.e_conomic.invoice.data.entities.InvoiceEntity
import com.e_conomic.invoice.data.localdatabase.InvoicesDao
import com.e_conomic.invoice.data.repository.InvoiceRepositoryImpl
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class InvoiceRepositoryImplTest {

    @MockK
    private lateinit var invoicesDao: InvoicesDao

    private lateinit var invoiceRepository: InvoiceRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        invoiceRepository = InvoiceRepositoryImpl(invoicesDao)
    }

    @Test
    fun saveInvoiceShouldInsertInvoice() = runTest {
        val invoice = InvoiceEntity("1", "Invoice 1", "8944549845654", 100.0f)

        coEvery { invoicesDao.insertInvoice(invoice) } just Runs

        invoiceRepository.saveInvoice(invoice)

        coVerify { invoicesDao.insertInvoice(invoice) }
    }

    @Test
    fun updateInvoiceShouldUpdateInvoice() = runTest {
        val invoice = InvoiceEntity("1", "Updated Invoice", "5646548748789", 200.0f)

        coEvery { invoicesDao.updateNote(invoice) } just Runs

        invoiceRepository.updateInvoiceLocal(invoice)

        coVerify { invoicesDao.updateNote(invoice) }
    }

    @Test
    fun deleteInvoiceShouldCallDaoDeleteMethod() = runTest {
        val invoice = InvoiceEntity("1", "Invoice to Delete", "546548978789", 100.0f)

        coEvery { invoicesDao.deleteNote(invoice) } just Runs

        invoiceRepository.deleteInvoice(invoice)

        coVerify { invoicesDao.deleteNote(invoice) }
    }

    @Test
    fun getInvoicesShouldReturnFlowOfInvoices() = runTest {
        val invoicesList = listOf(
            InvoiceEntity("1", "Invoice 1", "121244444", 100.0f),
            InvoiceEntity("2", "Invoice 2", "444455455", 200.0f)
        )

        coEvery { invoicesDao.getAllNotes() } returns flowOf(invoicesList)

        val result = invoiceRepository.getInvoices().first()

        assertEquals(invoicesList, result)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
