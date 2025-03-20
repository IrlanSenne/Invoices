package com.e_conomic.invoice

import com.e_conomic.invoice.core.MainViewModel
import com.e_conomic.invoice.data.OnEvent
import com.e_conomic.invoice.data.Resource
import com.e_conomic.invoice.data.entities.InvoiceEntity
import com.e_conomic.invoice.data.usecases.DeleteInvoiceUseCase
import com.e_conomic.invoice.data.usecases.GetInvoicesUseCase
import com.e_conomic.invoice.data.usecases.SaveInvoiceUseCase
import com.e_conomic.invoice.data.usecases.UpdateInvoiceUseCase
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @MockK
    private lateinit var getInvoicesUseCase: GetInvoicesUseCase

    @MockK
    private lateinit var saveInvoiceUseCase: SaveInvoiceUseCase

    @MockK
    private lateinit var deleteInvoiceUseCase: DeleteInvoiceUseCase

    @MockK
    private lateinit var updateInvoiceUseCase: UpdateInvoiceUseCase

    private lateinit var viewModel: MainViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        coEvery { getInvoicesUseCase.execute(Unit) } returns flowOf(listOf(
            InvoiceEntity(invoiceId = "1", title = "Invoice 1", totalAmount = 100.0f),
            InvoiceEntity(invoiceId = "1", title = "Invoice 2 Test", totalAmount = 2850.0f)
        ))

        viewModel = MainViewModel(
            getInvoicesUseCase = getInvoicesUseCase,
            saveInvoiceUseCase = saveInvoiceUseCase,
            deleteInvoiceUseCase = deleteInvoiceUseCase,
            updateInvoiceUseCase = updateInvoiceUseCase
        )
    }

    @Test
    fun `addInvoiceUpdatesSuccessfully`() = runTest {
        val mockInvoice =   InvoiceEntity(invoiceId = "1", title = "Invoice 1", totalAmount = 100.0f)

        coEvery { saveInvoiceUseCase.execute(mockInvoice) } just Runs

        viewModel.onEvent(OnEvent.AddInvoice(mockInvoice))

        advanceUntilIdle()

        coVerify { saveInvoiceUseCase.execute(mockInvoice) }
    }

    @Test
    fun `fetchInvoicesSuccessfully`() = runTest {
        val mockInvoices = listOf(
            InvoiceEntity(invoiceId = "1", title = "Invoice 1", totalAmount = 100.0f),
            InvoiceEntity(invoiceId = "15", title = "Invoice 51", totalAmount = 150.0f),
        )

        coEvery { getInvoicesUseCase.execute(Unit) } returns flowOf(mockInvoices)

        viewModel.onEvent(OnEvent.GetInvoices)
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.getInvoiceFlow.value is Resource.Success)
        assertEquals(mockInvoices, (viewModel.getInvoiceFlow.value as Resource.Success).result)
    }

    @Test
    fun `updateInvoiceSuccessfully`() = runTest {
        val mockInvoice =   InvoiceEntity(invoiceId = "1", title = "Invoice 1", totalAmount = 100.0f)

        coEvery { updateInvoiceUseCase.execute(mockInvoice) } just Runs

        viewModel.onEvent(OnEvent.UpdateInvoice(mockInvoice))

        advanceUntilIdle()

        coVerify { updateInvoiceUseCase.execute(mockInvoice) }
    }

    @Test
    fun `deleteInvoicSuccessfully`() = runTest {
        val mockInvoice =   InvoiceEntity(invoiceId = "1", title = "Invoice 1", totalAmount = 100.0f)

        coEvery { deleteInvoiceUseCase.execute(mockInvoice) } just Runs

        viewModel.onEvent(OnEvent.DeleteInvoice(mockInvoice))

        advanceUntilIdle()

        coVerify { deleteInvoiceUseCase.execute(mockInvoice) }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}